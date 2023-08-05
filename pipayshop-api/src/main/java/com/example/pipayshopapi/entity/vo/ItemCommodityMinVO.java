package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * fileName: ItemCommodityMinVO
 * author: 四面神
 * createTime:2023/8/5 14:56
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCommodityMinVO {

    /**
     * 价格
     */
    private Integer price;

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 商品介绍
     */
    private String details;

    /**
     * 上架时间
     */
    private Date createTime;

    /**
     *  商品名称
     */
    private String itemCommodityName;

    /**
     * 商品的展示图路径
     */
    private String avatarImag;

}
