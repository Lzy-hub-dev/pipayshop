package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCommodityInfoVO {
    /**
     * 商品id
     */
    private String commodityId;
    /**
     * 商店的商品名字
     */
    private String shopCommodityName;
    /**
     * 商店的商品第一张照片
     */
    private String firstPicture;
    /**
     * 商品的状态
     */
    private Integer status;


    /**
     * 用户id
     */
    private String uid;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userImage;
}
