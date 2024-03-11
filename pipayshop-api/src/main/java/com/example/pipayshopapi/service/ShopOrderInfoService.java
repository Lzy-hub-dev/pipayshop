package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopOrderInfo;
import com.example.pipayshopapi.entity.vo.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实体店订单数据表 服务类
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
public interface ShopOrderInfoService extends IService<ShopOrderInfo> {

    List<OrderListVO> getOrderList(String userId);

    int delOrderByOrderId(String orderId);

    ShopOrderDetailVO getOrderDetail(String orderId);

    int completedOrder(String orderId);

    int failOrder(String orderId);

    int failLiveOrder(String orderId);

    void deleteFailOrders();

    String generateUnpaidOrder(String token);

    boolean payOrder(String token);

    PageDataVO getOrderListByShopId(GetOrderDataVO getOrderDataVO);
    /**
     * 未支付订单改价接口
     */
    int changePrice(String token);

    PageDataVO getOrderLiveList(GetOrderDataVO getOrderDataVO);

    PageDataVO getOrderLiveListByShopId(GetOrderDataVO getOrderDataVO);

    ShopLiveOrderDetailVO getLiveOrderDetail(String orderId);

    String generateUnpaidLiveOrder(String token);

    /**
     * 返回订单order_id
     * @param token
     * @return
     */
    Map<String,String> payLiveOrder(String token);
}
