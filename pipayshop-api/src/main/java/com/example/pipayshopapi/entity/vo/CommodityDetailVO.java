package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityDetailVO {
    private String commodityId;
    private String brandId;
    private String itemCommodityName;
    private String originPrice;
    private String colorList;
    private String sizeList;
    private String originAddress;
    private String acceptAddressList;
    private String itemId;
    private String price;
    private String details;
    private String imagsList;
    private Integer inventory;
    private Integer freeShippingNum;
    private Integer categoryId;
    private String couponsList;
    private String tagList;
    private Integer degreeLoss;
}
