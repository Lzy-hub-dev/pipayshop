package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.GetOrderDataVO;
import com.example.pipayshopapi.entity.vo.ItemOrderInfoVO;
import com.example.pipayshopapi.entity.vo.OrderDetailVO;
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
    /**
     * 通过用户id去查找网店订单
     * @param userId
     * @return
     */
    List<ItemOrderInfoVO> selectUserItemOrders(String userId);

    /**
     * 通过orderId逻辑删除订单
     * @param orderId
     * @return
     */
    Boolean deleteUserItemOrder(String orderId);

    /**
     * 根据用户id查询 订单列表
     * @param userId
     * @return
     */
    List<ItemOrderInfoVO> selectOrderByUerId(String userId);

    /**
     * 根据卖家id查询网店关联的订单
     * @param userId 卖家id
     * @param orderStatus 0:待支付;2:已完成;3:查询所有
     * @return
     */
    List<ItemOrderInfoVO> itemOrders(String userId, Integer orderStatus);

    PageDataVO getOrderList(GetOrderDataVO getOrderDataVO);

    int delOrderByOrderId(String orderId);

    OrderDetailVO getOrderDetail(String orderId);

    int completedOrder(String orderId);

    int failOrder(String orderId);

    void deleteFailOrders();
}
