package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.example.pipayshopapi.entity.vo.ItemOrderDetailVO;
import com.example.pipayshopapi.entity.vo.OrderListVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;

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


    List<OrderListVO> getOrderList(String userId);

    int delOrderByOrderId(String orderId);

    ItemOrderDetailVO getOrderDetail(String orderId);

    int completedOrder(String orderId);

    void failOrder(String orderId);

    void deleteFailOrders();

    String generateUnpaidOrder(String token);

    boolean payOrder(String token);

    /**
     * 根据用户id查询网店的所有订单
     */
    PageDataVO getMyOrderByUid(Integer page,Integer limit,String uid,Integer status);
    /**
     * 未支付订单改价接口
     */
    int changePrice(String token);
}
