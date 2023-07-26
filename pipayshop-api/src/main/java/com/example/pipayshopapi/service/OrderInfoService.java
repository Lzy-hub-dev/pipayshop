package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.OrderPageVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;

/**
 * <p>
 * 订单数据表 服务类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
public interface OrderInfoService extends IService<OrderInfo> {


    /**
     * 用户的全部订单列表的分页展示
     */
    PageDataVO OrderList(OrderPageVO orderPageVO);

    /**
     * 用户的不同状态的订单列表的分页展示
     */
    PageDataVO OrderListByStatus(OrderPageVO orderPageVO);

    /**
     * 订单生成(待支付状态)
     */
    boolean addOrder(OrderInfo orderInfo);

    /**
     * 支付订单(已支付状态)
     */
    boolean payOrder(String orderId);

    /**
     * 完成订单(已完成状态)
     */
    boolean finishOrder(String orderId);

    /**
     * 订单的删除(逻辑删除)
     */
    boolean logicDeleteOrder(String orderId);

    /**
     * 订单的删除(真实删除)
     */
    boolean realDeleteOrder(String orderId);

}
