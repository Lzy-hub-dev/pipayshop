package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.example.pipayshopapi.entity.ShopOrderInfo;
import com.example.pipayshopapi.entity.vo.GetOrderDataVO;
import com.example.pipayshopapi.entity.vo.OrderListVO;
import com.example.pipayshopapi.entity.vo.PayOrderVO;
import com.example.pipayshopapi.entity.vo.ShopOrderDetailVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.mapper.ShopCommodityInfoMapper;
import com.example.pipayshopapi.mapper.ShopOrderInfoMapper;
import com.example.pipayshopapi.service.ShopOrderInfoService;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * @author wzx
 */
@Service
public class ShopOrderInfoServiceImpl extends ServiceImpl<ShopOrderInfoMapper, ShopOrderInfo> implements ShopOrderInfoService {

    @Resource
    ShopOrderInfoMapper shopOrderInfoMapper;

    @Resource
    AccountInfoMapper accountInfoMapper;

    @Resource
    ShopCommodityInfoMapper shopCommodityInfoMapper;

    @Override
    public List<OrderListVO> getOrderList(GetOrderDataVO getOrderDataVO) {
        return shopOrderInfoMapper.getOrderList(getOrderDataVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delOrderByOrderId(String orderId) {
        if (orderId == null || "".equals(orderId)){
            return 0;
        }
        return shopOrderInfoMapper.update(null, new UpdateWrapper<ShopOrderInfo>()
                .eq("order_id", orderId)
                .set("del_flag", 1)
                .set("update_time", new Date()));
    }

    @Override
    public ShopOrderDetailVO getOrderDetail(String orderId) {
        ShopOrderDetailVO shopOrderDetailVO = shopOrderInfoMapper.getShopOrderDetailVO(orderId);
        shopOrderDetailVO.setOrderId(orderId);
        return shopOrderDetailVO;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int completedOrder(String orderId) {
        return shopOrderInfoMapper.update(null, new UpdateWrapper<ShopOrderInfo>()
                .eq("order_id", orderId)
                .set("order_status", 2)
                .set("update_time", new Date()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int failOrder(String orderId) {
        return shopOrderInfoMapper.update(null, new UpdateWrapper<ShopOrderInfo>()
                .eq("order_id", orderId)
                .set("order_status", 3)
                .set("update_time", new Date()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFailOrders() {
        shopOrderInfoMapper.update(null, new UpdateWrapper<ShopOrderInfo>()
                .eq("order_status", 3)
                .set("del_flag", 1)
                .set("update_time", new Date()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generateUnpaidOrder(ItemOrderInfo itemOrderInfo) {
        // 生成orderId
        String orderId = StringUtil.generateShortId();
        ShopOrderInfo shopOrderInfo = new ShopOrderInfo(null, orderId, itemOrderInfo.getTransactionAmount(), null, null, itemOrderInfo.getCommodityId()
                , itemOrderInfo.getUid(), itemOrderInfo.getItemId(), null, null,itemOrderInfo.getNumber());
        int insert = shopOrderInfoMapper.insert(shopOrderInfo);
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
        // 商品库存 、 月售量更新
        int update = shopCommodityInfoMapper.update(null, new UpdateWrapper<ShopCommodityInfo>()
                .eq("commodity_id", payOrderVO.getCommodityId())
                .setSql("residue = residue - " + payOrderVO.getNumber())
                .setSql("monthly_sales = monthly_sales + " + payOrderVO.getNumber())
                .set("update_time", new Date()));
        if (update < 1){throw new RuntimeException();}
        // 订单状态、修改时间更新
        int update1 = shopOrderInfoMapper.update(null, new UpdateWrapper<ShopOrderInfo>()
                .eq("order_id", payOrderVO.getOrderId())
                .set("order_status", 1)
                .set("update_time", new Date()));
        if (update1 < 1){throw new RuntimeException();}
        return true;
    }
}
