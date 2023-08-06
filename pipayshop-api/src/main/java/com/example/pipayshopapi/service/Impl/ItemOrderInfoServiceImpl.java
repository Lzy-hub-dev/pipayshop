package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.mapper.ItemCommodityInfoMapper;
import com.example.pipayshopapi.mapper.ItemOrderInfoMapper;
import com.example.pipayshopapi.service.ItemOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    @Override
    public PageDataVO getOrderList(GetOrderDataVO getOrderDataVO) {
        getOrderDataVO.setCurrentPage((getOrderDataVO.getCurrentPage()-1)*getOrderDataVO.getPageSize());
        List<OrderListVO> orderList = itemOrderInfoMapper.getOrderList(getOrderDataVO);
        getOrderDataVO.setPageSize(null);
        List<OrderListVO> count = itemOrderInfoMapper.getOrderList(getOrderDataVO);
        if (count == null) {
            return new PageDataVO();
        }
        if (orderList == null || orderList.size() == 0) {
            return new PageDataVO();
        }
        return new PageDataVO(count.size(), orderList);
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
        return itemOrderInfoMapper.update(null, new UpdateWrapper<ItemOrderInfo>()
                .eq("order_id", orderId)
                .set("order_status", 3)
                .set("update_time", new Date()));
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
        itemOrderInfo.setOrderId(orderId);
        int insert = itemOrderInfoMapper.insert(itemOrderInfo);
        if (insert < 1){
            String message = "生成未支付订单失败";
            throw new BusinessException(message);
        }
        return orderId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean payOrder(PayOrderVO payOrderVO) {
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
}
