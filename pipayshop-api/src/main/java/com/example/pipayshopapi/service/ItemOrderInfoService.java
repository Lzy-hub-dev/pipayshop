package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.ItemOrderInfoVO;

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
}
