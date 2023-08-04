package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author:
 * @ClassName itemCommoditysVO
 * @Description TODO
 * @date 2023/8/4 11:15
 * @Version 1.0
 */
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
     * 商品首图
     */
    private String commodityPic;

    /**
     * 商品id
     */
    private String commodityId;
    /**
     * 商品介绍
     */
    private String details;
}