package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ThinkPad
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyItemCommodityDTO {

    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 商品现价
     */
    private BigDecimal price;

    /**
     * pi币
     */
    private BigDecimal piBalance;
    /**
     *  pi钱包地址
     */
    private String piAddress;
    /**
     *  商品名字
     */
    private String itemCommodityName;

    /**
     * 发货地
     */
    private String originAddress;
    /**
     * 支持发货的地区集合
     */
    private List<String> acceptAddressList;
    /**
     *  网店id
     */
    private String itemId;
    /**
     *  商品介绍
     */
    private String details;
    /**
     * 库存
     */
    private Integer inventory;
    /**
     *  免运费的起始订购数量
     */
    private Integer freeShippingNum;
    /**
     *  商品类别(外键关联)
     */
    private Integer categoryId;
    /**
     *  商品的折损（枚举）
     */
    private Integer degreeLoss;
    /**
     *  发货人的电话
     */
    private String originPhone;
    /**
     *  发货人的名字
     */
    private String originName;
    /**
     *  商品图片的地址集合
     */
    private List<String> imagsList;
    /**
     *  商品详情图片的地址集合
     */
    private List<String> detailImags;
    /**
     * 用户所在的国家编码
     */
    private String countryCode;


}
