package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author ThinkPad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexShopInfoVO {
    private String shopId;
    private String shopName;
    private Double score;
    private BigDecimal localhostLatitude;
    private BigDecimal localhostLongitude;
    private String userImage;

    private String shopIntroduce;
}
