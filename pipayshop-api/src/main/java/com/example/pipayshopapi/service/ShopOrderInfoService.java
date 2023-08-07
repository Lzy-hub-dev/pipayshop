package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.example.pipayshopapi.entity.ShopOrderInfo;
import com.example.pipayshopapi.entity.vo.*;

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

    PageDataVO getOrderList(GetOrderDataVO getOrderDataVO);

    int delOrderByOrderId(String orderId);

    ShopOrderDetailVO getOrderDetail(String orderId);

    int completedOrder(String orderId);

    int failOrder(String orderId);

    void deleteFailOrders();

    String generateUnpaidOrder(ShopOrderInfo shopOrderInfo);

    boolean payOrder(PayOrderVO payOrderVO);

    List<OrderListVO> getOrderListByShopId(GetOrderDataVO getOrderDataVO);
}
