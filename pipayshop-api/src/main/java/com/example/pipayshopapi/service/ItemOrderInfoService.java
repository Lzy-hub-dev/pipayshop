package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.dto.CompleteDTO;
import com.example.pipayshopapi.entity.dto.IncompleteDTO;
import com.example.pipayshopapi.entity.dto.PaymentDTO;
import com.example.pipayshopapi.entity.vo.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 商户订单数据表 服务类
 * </p>
 *
 * @author zxb
 * @since 2023-07-28
 */
public interface ItemOrderInfoService extends IService<ItemOrderInfo> {


    PageDataVO getOrderList(GetOrderDataVO getOrderDataVO);

    int delOrderByOrderId(String orderId);

    OrderDetailVO getOrderDetail(String orderId);

    int completedOrder(String orderId);

    int failOrder(String orderId);

    void deleteFailOrders();

    String generateUnpaidOrder(ItemOrderInfo itemOrderInfo);

    boolean payOrder(PayOrderVO payOrderVO);

    /**
     * 根据用户id查询网店的所有订单
     */
    PageDataVO getMyOrderByUid(Integer page,Integer limit,String uid,Integer status);
}
