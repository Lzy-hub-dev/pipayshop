package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CommodityVO {


    private String commodityId;
    private String itemCommodityName;
    private String originPrice;
    private String price;
    private String details;
    private String avatarImag;
    /**
     * 是否会员(0映射为false，1映射为true)
     */
    private Boolean membership;

}
