package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.config.QueueConfig;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.example.pipayshopapi.entity.ShopCommodityLiveInfo;
import com.example.pipayshopapi.entity.ShopOrderInfo;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.mapper.ShopCommodityInfoMapper;
import com.example.pipayshopapi.mapper.ShopCommodityLiveInfoMapper;
import com.example.pipayshopapi.mapper.ShopOrderInfoMapper;
import com.example.pipayshopapi.service.ShopOrderInfoService;
import com.example.pipayshopapi.util.StringUtil;
import com.example.pipayshopapi.util.TokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private ShopCommodityLiveInfoMapper shopCommodityLiveInfoMapper;

    @Override
    public List<OrderListVO> getOrderList(String userId) {
        return shopOrderInfoMapper.getOrderList(userId);
    }

    @Override
    public PageDataVO getOrderLiveList(GetOrderDataVO getOrderDataVO) {
        getOrderDataVO.setCurrentPage((getOrderDataVO.getCurrentPage()-1)*getOrderDataVO.getPageSize());
        Integer allOrderLiveList = shopOrderInfoMapper.getAllOrderLiveList(getOrderDataVO);
        List<OrderLiveListVO> orderLiveList = shopOrderInfoMapper.getOrderLiveList(getOrderDataVO);
        return new PageDataVO(allOrderLiveList,orderLiveList);
    }



    @Override
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
        return shopOrderInfoMapper.getShopOrderDetailVO(orderId);
    }


    @Override
    public int completedOrder(String orderId) {
        return shopOrderInfoMapper.update(null, new UpdateWrapper<ShopOrderInfo>()
                .eq("order_id", orderId)
                .set("order_status", 2)
                .set("update_time", new Date()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int failOrder(String orderId) {
        ShopOrderInfo one = shopOrderInfoMapper.selectOne(new LambdaQueryWrapper<ShopOrderInfo>()
                .eq(ShopOrderInfo::getOrderId, orderId)
                .eq(ShopOrderInfo::getDelFlag, 0)
                .eq(ShopOrderInfo::getOrderStatus, 0));
        if (one == null) {
            return 0;
        }
        int i = shopCommodityInfoMapper.addStock(one.getNumber(), one.getCommodityId());
        int i1 = shopOrderInfoMapper.update(null, new UpdateWrapper<ShopOrderInfo>()
                .eq("order_id", orderId)
                .set("order_status", 3)
                .set("update_time", new Date()));
        if (i < 1 || i1 < 1) {
            throw new RuntimeException();
        }

        return 1;
    }

    @Override
    public int failLiveOrder(String orderId) {
        ShopOrderInfo one = shopOrderInfoMapper.selectOne(new LambdaQueryWrapper<ShopOrderInfo>()
                .eq(ShopOrderInfo::getOrderId, orderId)
                .eq(ShopOrderInfo::getDelFlag, 0)
                .eq(ShopOrderInfo::getOrderStatus, 0));
        if (one == null) {
            return 0;
        }
        int i = shopCommodityLiveInfoMapper.addLiveStock(one.getNumber(), one.getCommodityId());
        int i1 = shopOrderInfoMapper.update(null, new UpdateWrapper<ShopOrderInfo>()
                .eq("order_id", orderId)
                .set("order_status", 3)
                .set("update_time", new Date()));
        if (i < 1 || i1 < 1) {
            throw new RuntimeException();
        }

        return 1;
    }

    @Override
    public void deleteFailOrders() {
        shopOrderInfoMapper.update(null, new UpdateWrapper<ShopOrderInfo>()
                .eq("order_status", 3)
                .set("del_flag", 1)
                .set("update_time", new Date()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generateUnpaidOrder(String token) {
        // 解析令牌
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        BigDecimal transactionAmount = BigDecimal.valueOf(Double.parseDouble(dataFromToken.get("transactionAmount", String.class)));
        String commodityId = dataFromToken.get("commodityId", String.class);
        String uid = dataFromToken.get("uid", String.class);
        String shopId = dataFromToken.get("shopId", String.class);
        Integer number = dataFromToken.get("number", Integer.class);
        // 生成orderId
        String orderId = StringUtil.generateShortId();
        ShopOrderInfo info = new ShopOrderInfo(orderId, transactionAmount, commodityId, uid, shopId, number);
        //库存递减
        int reduce = shopCommodityInfoMapper.reduceStock(number, commodityId);
        int insert = shopOrderInfoMapper.insert(info);

        if (insert < 1 || reduce < 1) {
            String message = "生成未支付订单失败";
            throw new BusinessException(message);
        }
        // 订单十分钟未支付的失效处理
        String pre = "shop_";
        rabbitTemplate.convertAndSend(QueueConfig.QUEUE_MESSAGE_DELAY, pre+orderId, message1 -> {
            message1.getMessageProperties().setExpiration(1000*60*10+"");
            return message1;
        });
        return orderId;
    }

    /**
     * 生成酒店未支付订单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generateUnpaidLiveOrder(String token) {
        // 解析令牌
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        BigDecimal transactionAmount = BigDecimal.valueOf(Double.parseDouble(dataFromToken.get("transactionAmount", String.class)));
        String commodityId = dataFromToken.get("commodityId", String.class);
        String uid = dataFromToken.get("uid", String.class);
        String shopId = dataFromToken.get("shopId", String.class);
        Integer number = dataFromToken.get("number", Integer.class);
        // 生成orderId
        String orderId = StringUtil.generateShortId();
        ShopOrderInfo info = new ShopOrderInfo(orderId, transactionAmount, commodityId, uid, shopId, number);
        //库存递减
        int reduce = shopCommodityLiveInfoMapper.reduceLiveStock(number,commodityId);
        int insert = shopOrderInfoMapper.insert(info);

        if (insert < 1 || reduce < 1) {
            String message = "生成未支付订单失败";
            throw new BusinessException(message);
        }
        // 订单十分钟未支付的失效处理
        String pre = "shopLive_";
        rabbitTemplate.convertAndSend(QueueConfig.QUEUE_MESSAGE_DELAY, pre+orderId, message1 -> {
            message1.getMessageProperties().setExpiration(1000*60*10+"");
            return message1;
        });
        return orderId;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean payOrder(String token) {
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String orderId = dataFromToken.get("orderId", String.class);
        String uid1 = dataFromToken.get("uid", String.class);
        BigDecimal transactionAmount = BigDecimal.valueOf(Double.parseDouble(dataFromToken.get("transactionAmount", String.class)));
        String commodityId = dataFromToken.get("commodityId", String.class);
        Integer number = dataFromToken.get("number", Integer.class);

        // 校验订单id是否已经存在，保证接口的幂等性，避免重复下单
        Long count = shopOrderInfoMapper.selectCount(new QueryWrapper<ShopOrderInfo>()
                .eq("order_id", orderId)
                .eq("order_status", 1));
        if (count != 0){throw new BusinessException("该订单已经支付，请勿重复下单！");}
        // 用户余额更新
        int uid = accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                .eq("uid", uid1)
                .setSql("point_balance = point_balance - " + transactionAmount)
                .set("update_time", new Date()));
        if (uid < 1){throw new RuntimeException();}
        // 商品库存 、 月售量更新
        int update = shopCommodityInfoMapper.update(null, new UpdateWrapper<ShopCommodityInfo>()
                .eq("commodity_id", commodityId)
                .setSql("residue = residue - " + number)
                .setSql("monthly_sales = monthly_sales + " + number)
                .set("update_time", new Date()));
        if (update < 1){throw new RuntimeException();}
        // 订单状态、修改时间更新
        int update1 = shopOrderInfoMapper.update(null, new UpdateWrapper<ShopOrderInfo>()
                .eq("order_id", orderId)
                .set("order_status", 1)
                .set("update_time", new Date()));
        if (update1 < 1){throw new RuntimeException();}
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean payLiveOrder(String token) {
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String orderId = dataFromToken.get("orderId", String.class);
        String uid1 = dataFromToken.get("uid", String.class);
        BigDecimal transactionAmount = BigDecimal.valueOf(Double.parseDouble(dataFromToken.get("transactionAmount", String.class)));
        String commodityId = dataFromToken.get("commodityId", String.class);
        Integer number = dataFromToken.get("number", Integer.class);

        // 校验订单id是否已经存在，保证接口的幂等性，避免重复下单
        Long count = shopOrderInfoMapper.selectCount(new QueryWrapper<ShopOrderInfo>()
                .eq("order_id", orderId)
                .eq("order_status", 1));
        if (count != 0){throw new BusinessException("该订单已经支付，请勿重复下单！");}
        // 用户余额更新
        int uid = accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                .eq("uid", uid1)
                .setSql("point_balance = point_balance - " + transactionAmount)
                .set("update_time", new Date()));
        if (uid < 1){throw new RuntimeException();}
        // 商品库存 、 月售量更新
        int update = shopCommodityLiveInfoMapper.update(null, new UpdateWrapper<ShopCommodityLiveInfo>()
                .eq("room_id", commodityId)
                .setSql("inventory = inventory - " + number)
                .setSql("monthly_sales = monthly_sales + " + number));
        if (update < 1){throw new RuntimeException();}
        // 订单状态、修改时间更新
        int update1 = shopOrderInfoMapper.update(null, new UpdateWrapper<ShopOrderInfo>()
                .eq("order_id", orderId)
                .set("order_status", 1)
                .set("update_time", new Date()));
        if (update1 < 1){throw new RuntimeException();}
        return true;
    }

    @Override
    public PageDataVO getOrderListByShopId(GetOrderDataVO getOrderDataVO) {
        getOrderDataVO.setCurrentPage((getOrderDataVO.getCurrentPage() - 1) * getOrderDataVO.getPageSize());
        List<OrderListVO> voList = shopOrderInfoMapper.getOrderListByShopId(getOrderDataVO);
        Integer count = shopOrderInfoMapper.getOrderListCountByShopId(getOrderDataVO);
        return new PageDataVO(count,voList);
    }

    //卖家的酒店所有订单展示
    @Override
    public PageDataVO getOrderLiveListByShopId(GetOrderDataVO getOrderDataVO) {
        getOrderDataVO.setCurrentPage((getOrderDataVO.getCurrentPage() - 1) * getOrderDataVO.getPageSize());
        Integer allOrderLiveListByShopId = shopOrderInfoMapper.getAllOrderLiveListByShopId(getOrderDataVO);
        List<OrderLiveListVO> orderLiveListByShopId = shopOrderInfoMapper.getOrderLiveListByShopId(getOrderDataVO);
        return new PageDataVO(allOrderLiveListByShopId,orderLiveListByShopId);
    }

    @Override
    public ShopLiveOrderDetailVO getLiveOrderDetail(String orderId) {
        ShopLiveOrderDetailVO liveOrderDetail = shopOrderInfoMapper.getLiveOrderDetail(orderId);
        return liveOrderDetail;
    }



    /**
     * 未支付订单改价接口
     *
     */
    @Override
    public int changePrice(String token) {
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String orderId = dataFromToken.get("orderId", String.class);
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(dataFromToken.get("price", String.class)));
        if (price.doubleValue() < 0) {
            throw new BusinessException("输入的金额不合法");
        }
        return shopOrderInfoMapper.update(null, new LambdaUpdateWrapper<ShopOrderInfo>()
                .eq(ShopOrderInfo::getOrderId, orderId)
                .eq(ShopOrderInfo::getOrderStatus, 0)
                .eq(ShopOrderInfo::getDelFlag, 0)
                .set(ShopOrderInfo::getTransactionAmount, price));
    }


}
