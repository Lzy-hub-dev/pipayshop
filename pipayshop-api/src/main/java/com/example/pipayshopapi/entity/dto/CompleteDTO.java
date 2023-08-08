package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ThinkPad
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteDTO {

    // PI支付ID
    private String paymentId;

    // txId
    private String txId;

    // 网店订单ID【余额支付参数】
    private String itemOrderId;

    // 实体店订单ID【余额支付参数】
    private String shopOrderId;

    // 充值订单ID【余额支付参数】
    private String rechargeOrderId;



    // 支付方式：0：PI钱包 1：积分支付
    private String payType;
}
