package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wzx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCommoditiesVO{
    /**
     * 商品现价
     */
    private BigDecimal price;

    /**
     * 商品现价
     */
    private BigDecimal piBalance;

    /**
     * 商品现价
     */
    private Integer piShoper;
    /**
     * 商品原价
     */
    private BigDecimal originPrice;
    /**
     * 商品名字
     */
    private String itemCommodityName;
    /**
     * 店铺id
     */
    private String shopId;

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
