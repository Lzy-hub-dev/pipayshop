package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.example.pipayshopapi.entity.ShopOrderInfo;
import com.example.pipayshopapi.entity.vo.GetOrderDataVO;
import com.example.pipayshopapi.entity.vo.OrderListVO;
import com.example.pipayshopapi.entity.vo.PayOrderVO;
import com.example.pipayshopapi.entity.vo.ShopOrderDetailVO;

import java.util.List;

/**
 * <p>
 * 实体店订单数据表 服务类
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
public interface ShopOrderInfoService extends IService<ShopOrderInfo> {

    List<OrderListVO> getOrderList(GetOrderDataVO getOrderDataVO);

    int delOrderByOrderId(String orderId);

    ShopOrderDetailVO getOrderDetail(String orderId);

    int completedOrder(String orderId);

    int failOrder(String orderId);

    void deleteFailOrders();

    String generateUnpaidOrder(ItemOrderInfo itemOrderInfo);

    boolean payOrder(PayOrderVO payOrderVO);
}
