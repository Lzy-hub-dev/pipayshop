package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.RechargePermissionsOrder;
import com.example.pipayshopapi.entity.vo.RechargePermissionsOrderVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxb
 * @since 2023-08-06
 */
public interface RechargePermissionsOrderService extends IService<RechargePermissionsOrder> {
    boolean addShopSum(String orderId);

    /**
     *
     */

    boolean updateUploadBalanceInfo(String orderId);

    String getUploadBalanceNoPayOrder(RechargePermissionsOrderVO rechargePermissionsOrderVO);
}
