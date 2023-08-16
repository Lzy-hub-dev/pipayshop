package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCartVO {

    /**
     * 购物车id
     */
    private String cartId;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 商品id
     */
    private String commodityId;


    private String itemId;
    /**
     * 商品现价
     */
    private BigDecimal price;
    /**
     * 商品名字
     */
    private String itemCommodityName;
    /**
     * 商品的展示图路径
     */
    private String avatarImag;
    /**
     * 免运费的起始订购数量
     */
    private Integer freeShippingNum;
    /**
     * 优惠卷的集合
     */
    private String couponsList;
    /**
     * 0:正常 1:上架 2:下架
     */
    private Integer status;

    /**
     * 商品的标签id集合
     */
    private String tagList;

    /**
     * 商品的数量
     */
    private String sumCount;

    /**
     * 商品规格
     */
    private String commoditySpec;
}
