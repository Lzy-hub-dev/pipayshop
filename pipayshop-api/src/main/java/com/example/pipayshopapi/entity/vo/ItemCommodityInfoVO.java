package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author mongdie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCommodityInfoVO {
    /**
     * 商品现价
     */
    private BigDecimal price;
    /**
     * 商品名字
     */
    private String itemCommodityName;
    /**
     * 发货地
     */
    private String originAddress;
    /**
     * 商品介绍
     */
    private String details;
    /**
     * 商品类别(外键关联)
     */
    private Integer categoryId;

}
