package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopInfoVO1 {

    /**
     * 店铺id
     */
    private String shopId;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 店铺评分
     */
    private Double score;
    /**
     * 店铺图片的路径集合
     */
    private String shopImagList;
    /**
     * 粉丝总数
     */
    private Integer follows;
}
