package com.example.pipayshopapi.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: jiangjiafeng
 * @ClassName ShopOrderDTO
 * @Description 
 * @date 2023/8/7 16:16
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopOrderDTO {

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 买家id
     */
    private String uid;

    /**
     * 卖家id
     */
    private String shopId;

    /**
     * 商品下单数量
     */
    private Integer number;
}
