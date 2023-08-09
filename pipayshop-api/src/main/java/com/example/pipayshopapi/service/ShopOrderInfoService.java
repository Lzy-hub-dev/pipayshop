package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.example.pipayshopapi.entity.ShopOrderInfo;
import com.example.pipayshopapi.entity.dto.ChangePriceDTO;
import com.example.pipayshopapi.entity.dto.ShopOrderDTO;
import com.example.pipayshopapi.entity.vo.*;

import java.math.BigDecimal;
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

    String generateUnpaidOrder(ShopOrderDTO shopOrderDTO);

    boolean payOrder(PayOrderVO payOrderVO);

    PageDataVO getOrderListByShopId(GetOrderDataVO getOrderDataVO);
    /**
     * 未支付订单改价接口
     */
    int changePrice(ChangePriceDTO priceDTO);

    PageDataVO getOrderLiveList(GetOrderDataVO getOrderDataVO);

    PageDataVO getOrderLiveListByShopId(GetOrderDataVO getOrderDataVO);

    ShopLiveOrderDetailVO getLiveOrderDetail(String orderId);
}
