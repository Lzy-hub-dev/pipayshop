package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author ThinkPad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private String paymentId;
    // 交易金额
    private BigDecimal amount;
    // 网店订单id
    private String itemOrderId;
    // 实体店订单id
    private String shopOrderId;
    // 充值订单id
    private String rechargeOrderId;


}
