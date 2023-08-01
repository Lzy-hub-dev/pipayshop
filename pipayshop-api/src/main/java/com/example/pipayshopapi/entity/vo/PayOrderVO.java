package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author wzx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderVO {

    private String orderId;

    private String uid;

    private String commodityId;

    private BigDecimal transactionAmount;

}
