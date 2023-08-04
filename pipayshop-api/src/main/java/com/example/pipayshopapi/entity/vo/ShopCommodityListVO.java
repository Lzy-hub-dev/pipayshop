package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author:
 * @ClassName ShopCommodityListVO
 * @Description 商品列表展示
 * @date 2023/8/4 16:19
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCommodityListVO {
    /**
     * 商品的id
     */
    private String commodityId;
    /**
     * 商品的名字
     */
    private String commodityName;

    /**
     * 商品的价格
     */
    private BigDecimal price;

    /**
     * 商品图片
     */
    private String avatarImag;

    /**
     * 访问时间
     */
    private String accessTime;
    /**
     * 商店名称
     */
    private String shopName;
    /**
     * 商家头像
     */
    private String userImage;

}
