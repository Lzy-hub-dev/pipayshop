package com.example.pipayshopapi.service.Impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.config.CommonConfig;
import com.example.pipayshopapi.config.RedisConfig;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.example.pipayshopapi.entity.dto.CompleteDTO;
import com.example.pipayshopapi.entity.dto.IncompleteDTO;
import com.example.pipayshopapi.entity.dto.PaymentDTO;
import com.example.pipayshopapi.entity.dto.TransactionDTO;
import com.example.pipayshopapi.entity.enums.PaymentEnum;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.mapper.ItemCommodityInfoMapper;
import com.example.pipayshopapi.mapper.ItemOrderInfoMapper;
import com.example.pipayshopapi.service.ItemOrderInfoService;
import com.example.pipayshopapi.util.HttpClientUtil;
import com.example.pipayshopapi.util.StringUtil;
import okhttp3.*;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 商户订单数据表 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-07-28
 */
@Service
public class ItemOrderInfoServiceImpl extends ServiceImpl<ItemOrderInfoMapper, ItemOrderInfo> implements ItemOrderInfoService {

    @Resource
    ItemOrderInfoMapper itemOrderInfoMapper;

    @Resource
    AccountInfoMapper accountInfoMapper;

    @Resource
    ItemCommodityInfoMapper itemCommodityInfoMapper;

    @Resource
    private CommonConfig commonConfig;

    @Resource
    private RedissonClient redissonClient;
    @Override
    public PageDataVO getOrderList(GetOrderDataVO getOrderDataVO) {
        int pageSize = getOrderDataVO.getPageSize();
        int currentPage = getOrderDataVO.getCurrentPage();
        // 获取分页数据
        String userId = getOrderDataVO.getUserId();
        int orderStatus = getOrderDataVO.getOrderStatus();
        List<OrderListVO> orderList = itemOrderInfoMapper.getOrderList(orderStatus, userId
        ,(currentPage - 1) * pageSize, pageSize);
        // 获取总条数
        int count = itemOrderInfoMapper.getOrderListCount(userId, orderStatus);
        return new PageDataVO(count, orderList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delOrderByOrderId(String orderId) {
        if (orderId == null || "".equals(orderId)){
            return 0;
        }
        return itemOrderInfoMapper.update(null, new UpdateWrapper<ItemOrderInfo>()
                .eq("order_id", orderId)
                .set("del_flag", 1)
                .set("update_time", new Date()));
    }


    @Override
    public OrderDetailVO getOrderDetail(String orderId) {
        OrderDetailVO orderDetailVO = itemOrderInfoMapper.getOrderDetail(orderId);
        String userImage = itemCommodityInfoMapper.getItemIdByOrderId(orderId);
        OrderDetailVO orderDetailVOI = itemOrderInfoMapper.getOrderMinDeatail(orderId);
        orderDetailVO.setUserImage(userImage);
        orderDetailVO.setDetails(orderDetailVOI.getDetails());
        orderDetailVO.setAvatarImag(orderDetailVOI.getAvatarImag());

        return orderDetailVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int completedOrder(String orderId) {
        return itemOrderInfoMapper.update(null, new UpdateWrapper<ItemOrderInfo>()
                .eq("order_id", orderId)
                .set("order_status", 2)
                .set("update_time", new Date()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int failOrder(String orderId) {
        ItemOrderInfo itemOrderInfo = itemOrderInfoMapper.selectOne(new LambdaUpdateWrapper<ItemOrderInfo>()
                .eq(ItemOrderInfo::getOrderId, orderId)
                .eq(ItemOrderInfo::getDelFlag, 0));

        int i = itemCommodityInfoMapper.addStock(itemOrderInfo.getNumber(), itemOrderInfo.getCommodityId());
        int i1 = itemOrderInfoMapper.update(null, new UpdateWrapper<ItemOrderInfo>()
                .eq("order_id", orderId)
                .set("order_status", 3)
                .set("update_time", new Date()));
        if (i < 1 || i1 < 1) {
            throw new RuntimeException();
        }
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFailOrders() {
        itemOrderInfoMapper.update(null, new UpdateWrapper<ItemOrderInfo>()
                .eq("order_status", 3)
                .set("del_flag", 1)
                .set("update_time", new Date()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generateUnpaidOrder(ItemOrderInfo itemOrderInfo) {
        // 生成orderId
        String orderId = StringUtil.generateShortId();
        try {
            itemOrderInfo.setOrderId(orderId);
            int i = itemCommodityInfoMapper.reduceStock(itemOrderInfo.getNumber(), itemOrderInfo.getCommodityId());
            int insert = itemOrderInfoMapper.insert(itemOrderInfo);
            if (insert < 1 || i < 1) {
                String message = "生成未支付订单失败";
                throw new BusinessException(message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return orderId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean payOrder(PayOrderVO payOrderVO) {
        // 校验订单id是否已经存在，保证接口的幂等性，避免重复下单
        Long count = itemOrderInfoMapper.selectCount(new QueryWrapper<ItemOrderInfo>()
                .eq("order_id", payOrderVO.getOrderId())
                .eq("order_status", 1));
        if (count != 0){throw new BusinessException("该订单已经支付，请勿重复下单！");}
        // 用户余额更新
        int uid = accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                .eq("uid", payOrderVO.getUid())
                .setSql("point_balance = point_balance - " + payOrderVO.getTransactionAmount())
                .set("update_time", new Date()));
        if (uid < 1){throw new RuntimeException();}
        // 商品库存更新
        int update = itemCommodityInfoMapper.update(null, new UpdateWrapper<ItemCommodityInfo>()
                .eq("commodity_id", payOrderVO.getCommodityId())
                .setSql("inventory = inventory - "+payOrderVO.getNumber())
                .set("update_time", new Date()));
        if (update < 1){throw new RuntimeException();}
        // 订单状态、修改时间更新
        int update1 = itemOrderInfoMapper.update(null, new UpdateWrapper<ItemOrderInfo>()
                .eq("order_id", payOrderVO.getOrderId())
                .set("order_status", 1)
                .set("update_time", new Date()));
        if (update1 < 1){throw new RuntimeException();}
        return true;
    }


    /**
     * 根据用户id查询网店的所有订单
     */
    @Override
    public PageDataVO getMyOrderByUid(Integer page,Integer limit,String uid,Integer status) {
        Integer allMyOrderByUid = itemOrderInfoMapper.getAllMyOrderByUid(uid,status);
        List<MyItemOrderInfoVO> myOrderByUid = itemOrderInfoMapper.getMyOrderByUid((page - 1) * limit, limit, uid,status);
        return new PageDataVO(allMyOrderByUid,myOrderByUid);
    }

    /**
     * 未支付订单改价接口
     *
     * @param orderId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int changePrice(String orderId, BigDecimal price) {
        if (price.doubleValue() < 0) {
            throw new BusinessException("输入的金额不合法");
        }
        return itemOrderInfoMapper.update(null, new LambdaUpdateWrapper<ItemOrderInfo>()
                .eq(ItemOrderInfo::getOrderId, orderId)
                .eq(ItemOrderInfo::getOrderStatus, 0)
                .eq(ItemOrderInfo::getDelFlag, 0)
                .set(ItemOrderInfo::getTransactionAmount, price));
    }
}
