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
     * 实体店的头像
     */
    private String userImage;
    /**
     * 粉丝总数
     */
    private Integer follows;

    private Integer status;
}
