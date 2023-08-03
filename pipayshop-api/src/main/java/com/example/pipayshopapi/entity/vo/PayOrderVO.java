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

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;

    /**
     * 交易商品数量
     */
    private Integer number;

}
