package com.example.pipayshopapi.entity.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ShopInfoDTO {

    /**
     * 店铺id
     */
    private String shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 坐标维度
     */
    private BigDecimal localhostLatitude;

    /**
     * 坐标经度
     */
    private BigDecimal localhostLongitude;

    /**
     * 店铺的标签id集合
     */

    private String tagList;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 店铺地址详情
     */
    private String address;

    /**
     * 店铺简介（广告词）
     */
    private String shopIntroduce;

    /**
     * 店铺图片的路径集合
     */
    private String shopImagList;

    /**
     * 实体店的头像
     */
    private String userImage;

    /**
     * 店铺类别
     */
    private String categoryId;
    // 使用pi比例
    private String piratio;
    // 营业时间
    private String startTime;
    // 打烊时间
    private String endTime;
   //二维码
    private String qrcode;
}
