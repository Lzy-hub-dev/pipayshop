package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCommodityLiveVO {
    /**
     * 服务id
     */
    private String roomId;

    /**
     * 实体店id
     */
    private String shopId;

    /**
     * 服务描述
     */
    private String detail;

    /**
     * 服务标签集合
     */
    private String tagList;

    /**
     * 服务的头像
     */
    private String avatarImag;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 坐标维度
     */
    private BigDecimal localhostLatitude;

    /**
     * 坐标经度
     */
    private BigDecimal localhostLongitude;

    /**
     * 店铺地址
     */
    private String address;

    /**
     * 店铺评分
     */
    private Double score;

    /**
     * 评价
     */
    private String evaluate;
}
