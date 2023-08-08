package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.RechargeOrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.dto.CompleteDTO;
import com.example.pipayshopapi.entity.dto.IncompleteDTO;
import com.example.pipayshopapi.entity.dto.PaymentDTO;
import com.example.pipayshopapi.entity.vo.ResponseVO;

/**
 * <p>
 * 商户订单数据表 服务类
 * </p>
 *
 * @author zxb
 * @since 2023-08-08
 */
public interface RechargeOrderInfoService extends IService<RechargeOrderInfo> {

    ResponseVO incomplete(IncompleteDTO incompleteDTO);

    ResponseVO approve(PaymentDTO paymentDTO);

    ResponseVO complete(CompleteDTO completeDTO) throws InterruptedException;


    Boolean cancelled(String rechargeOrderId);
}
