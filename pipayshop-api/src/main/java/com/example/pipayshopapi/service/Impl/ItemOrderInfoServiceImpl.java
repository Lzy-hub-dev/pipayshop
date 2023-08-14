package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.example.pipayshopapi.entity.vo.ItemOrderDetailVO;
import com.example.pipayshopapi.entity.vo.MyItemOrderInfoVO;
import com.example.pipayshopapi.entity.vo.OrderListVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.mapper.ItemCommodityInfoMapper;
import com.example.pipayshopapi.mapper.ItemOrderInfoMapper;
import com.example.pipayshopapi.service.ItemOrderInfoService;
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
    private RabbitTemplate rabbitTemplate;
    @Override
    public List<OrderListVO> getOrderList(String userId) {
        return itemOrderInfoMapper.getOrderList(userId);
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
    public ItemOrderDetailVO getOrderDetail(String orderId) {
        return itemOrderInfoMapper.getOrderDetail(orderId);
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
                .eq(ItemOrderInfo::getDelFlag, 0)
                .eq(ItemOrderInfo::getOrderStatus, 0));
        if (itemOrderInfo == null){
            return 0;
        }
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
    public String generateUnpaidOrder(String token) {
        // 生成orderId
        String orderId = StringUtil.generateShortId();
        // 解析token
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String uid = dataFromToken.get("uid", String.class);
        String itemId = dataFromToken.get("itemId", String.class);
        String buyerDataId = dataFromToken.get("buyerDataId", String.class);
        BigDecimal transactionAmount = BigDecimal.valueOf(dataFromToken.get("transactionAmount", Double.class));
        String commodityId = dataFromToken.get("commodityId", String.class);
        Integer number = dataFromToken.get("number", Integer.class);
        String commoditySpecification = dataFromToken.get("commoditySpecification", String.class);
        // 生成插入的实体类
        ItemOrderInfo itemOrderInfo = new ItemOrderInfo(null, orderId, transactionAmount, commodityId, uid, itemId
                , null, null, null, null, buyerDataId, number, commoditySpecification);
        try {
            int i = itemCommodityInfoMapper.reduceStock(number, commodityId);
            int insert = itemOrderInfoMapper.insert(itemOrderInfo);
            if (insert < 1 || i < 1) {
                String message = "生成未支付订单失败";
                throw new BusinessException(message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        rabbitTemplate.convertAndSend(QueueConfig.QUEUE_MESSAGE_DELAY, "item_"+orderId, message1 -> {
//            message1.getMessageProperties().setExpiration("10000");
//            return message1;
//        });
        return orderId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean payOrder(String token) {
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String orderId = dataFromToken.get("orderId", String.class);
        String uid1 = dataFromToken.get("uid", String.class);
        BigDecimal transactionAmount = BigDecimal.valueOf(dataFromToken.get("transactionAmount", Double.class));
        String commodityId = dataFromToken.get("commodityId", String.class);
        Integer number = dataFromToken.get("number", Integer.class);
        // 校验订单id是否已经存在，保证接口的幂等性，避免重复下单
        Long count = itemOrderInfoMapper.selectCount(new QueryWrapper<ItemOrderInfo>()
                .eq("order_id", orderId)
                .eq("order_status", 1));
        if (count != 0){throw new BusinessException("该订单已经支付，请勿重复下单！");}
        // 用户余额更新
        int uid = accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                .eq("uid", uid1)
                .setSql("point_balance = point_balance - " + transactionAmount)
                .set("update_time", new Date()));
        if (uid < 1){throw new RuntimeException();}
        // 商品库存更新
        int update = itemCommodityInfoMapper.update(null, new UpdateWrapper<ItemCommodityInfo>()
                .eq("commodity_id", commodityId)
                .setSql("inventory = inventory - "+number)
                .set("update_time", new Date()));
        if (update < 1){throw new RuntimeException();}
        // 订单状态、修改时间更新
        int update1 = itemOrderInfoMapper.update(null, new UpdateWrapper<ItemOrderInfo>()
                .eq("order_id", orderId)
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
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int changePrice(String token) {
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String orderId = dataFromToken.get("orderId", String.class);
        BigDecimal price = BigDecimal.valueOf(dataFromToken.get("price", Double.class));
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
