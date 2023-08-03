package com.example.pipayshopapi.entity.vo;

import com.example.pipayshopapi.entity.ShopTags;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: jiangjiafeng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopInfoVO {
    /**
     * 店铺id
     */
    private String shopId;

    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 距离
     */
    private String distance;
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
     * 店铺简介（广告词）
     */
    private String shopIntroduce;

    /**
     * 标签列表
     */
    private List<ShopTags> shopTagsList;

    /**
     * 店铺图片的路径集合
     */
    private List<String> shopImagList;

    /**
     * 实体店的头像
     */
    private String userImage;

}
