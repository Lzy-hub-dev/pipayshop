package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
     * 0:正常 1:逻辑删除 2:真正删除
     */
    private Boolean delFlag;

    /**
     * 商品的标签id集合
     */
    private String tagList;

    /**
     * 商品的数量
     */
    private String sumNumber;

    /**
     * 商品的颜色集合
     */
    private String colorList;

    /**
     * 商品的尺寸集合
     */
    private String sizeList;

}
