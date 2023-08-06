package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.RechargePermissionsOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.RechargeVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxb
 * @since 2023-08-06
 */
public interface RechargePermissionsOrderService extends IService<RechargePermissionsOrder> {
    boolean addShopSum(RechargeVO rechargeVO);

    /**
     *
     */

}
