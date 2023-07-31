package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.example.pipayshopapi.entity.vo.GetOrderDataVO;
import com.example.pipayshopapi.entity.vo.OrderListVO;
import com.example.pipayshopapi.entity.vo.OrderDetailVO;
import com.example.pipayshopapi.mapper.ItemOrderInfoMapper;
import com.example.pipayshopapi.service.ItemOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    private ItemOrderInfoMapper itemOrderInfoMapper;

    @Override
    public List<OrderListVO> getOrderList(GetOrderDataVO getOrderDataVO) {
        return itemOrderInfoMapper.getOrderList(getOrderDataVO);
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
}
