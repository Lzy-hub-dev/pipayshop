package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ShopOrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.ItemOrderInfoVO;
import com.example.pipayshopapi.entity.vo.ShopOrderInfoVO;

import java.util.List;

/**
 * <p>
 * 订单数据表 服务类
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
public interface ShopOrderInfoService extends IService<ShopOrderInfo> {

    /**
     * 通过用户id去查找实体店订单
     * @param userId
     * @return
     */
    List<ShopOrderInfoVO> selectUserShopOrders(String userId);

}
