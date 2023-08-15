package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class itemCommoditiesVO {
    /**
     * 商品现价
     */
    private BigDecimal price;
    /**
     * 商品原价
     */
    private BigDecimal originPrice;
    /**
     * 商品名字
     */
    private String itemCommodityName;

    /**
     * 商品的展示图路径
     */
    private String avatarImag;
    /**
     * 是否会员(0映射为false，1映射为true)
     */
    private Boolean membership;
    /**
     * 商品id
     */
    private String commodityId;
    /**
     * 商品介绍
     */
    private String details;
}
