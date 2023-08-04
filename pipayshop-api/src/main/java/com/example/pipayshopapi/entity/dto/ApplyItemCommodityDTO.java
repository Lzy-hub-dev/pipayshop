package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author ThinkPad
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyItemCommodityDTO {
    private String brandId;
    private BigDecimal originPrice;
    private BigDecimal price;
    private String itemCommodityName;
    private String shopId;
    private String colorList;
    private String sizeList;
    private String originAddress;
    private String acceptAddressList;
    private String itemId;
    private String details;
    private Integer inventory;
    private Integer freeShippingNum;
    private Integer categoryId;
    private Integer degreeLoss;
    private String originPhone;
    private String originName;
    private String avatarImag;
    private String imagsList;
    private String detailImagList;

}
