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
     * 网店名字
     */
    private String itemName;
    /**
     * 发货地
     */
    private String originAddress;
    /**
     * 商品介绍
     */
    private String details;
    /**
     * 商品类别二级分类(外键关联)
     */
    private Integer categoryId;

    /**
     * 品牌id
     */
    private Integer brandId;


    /**
     * 损坏程度
     */
    private Integer degreeLoss;

    /**
     * 浏览时间
     */
    private String accessTime;

    /**
     * 商品首图
     */
    private String commodityPic;
    /**
     * 网店首图
     */
    private String sellerPic;
    /**
     * 商品id
     */
    private String commodityId;
    /**
     * 商品状态0:审核 1:上架 2:下架3:绝对删除
     */
    private String status;
}
