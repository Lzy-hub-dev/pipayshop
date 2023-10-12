package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.dto.PayPalDTO;
import com.example.pipayshopapi.entity.vo.AccountInfoVO;

/**
 * <p>
 * 用户账户表 服务类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
public interface AccountInfoService extends IService<AccountInfo> {

    /**
     * 根据用户Id查找用户账户表的积分余额和pi币余额
     * @param uid
     * @return
     * */
    AccountInfoVO selectAccountById(String uid);


    Object capturePayment(String orderId);

    Object createOrder(PayPalDTO payPalDTO);

}
