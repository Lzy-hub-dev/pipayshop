package com.example.pipayshopapi.entity.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ShopCommodityInfoDTO {

    /**
     * 店铺商品的id
     */
    private String commodityId;

    /**
     * 商品的名字
     */
    private String commodityName;

    /**
     * 实体店商品的头像
     */
    private String avatarImag;

    /**
     * 商品的图片路径集合
     */
    private String commodityImgList;

    /**
     * 商品详情
     */
    private String commodityDetail;

    /**
     * 商品的价格
     */
    private BigDecimal price;

    /**
     * 商品有效期
     */
    private Integer validityTime;

    /**
     * 商品剩余数量
     */
    private Integer residue;

    /**
     * 预定的注意事项
     */
    private String reservationInformation;


}
