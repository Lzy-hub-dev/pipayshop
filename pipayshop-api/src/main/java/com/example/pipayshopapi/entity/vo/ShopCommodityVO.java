package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCommodityVO {
    /**
     * 商品的id
     */
    private String commodityId;
    /**
     * 商品的名字
     */
    private String commodityName;
    /**
     * 商品详情
     */
    private String commodityDetail;
    /**
     * 商品的价格
     */
    private BigDecimal price;
    /**
     * 该商品的店铺id
     */
    private String shopId;

    /**
     * 店铺地址
     */
    private String address;
    /**
     * 商品图片
     */
    private String avatarImag;

    /**
     * 访问时间
     */
    private String accessTime;


}
