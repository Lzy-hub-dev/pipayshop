package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zxb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyShopDTO {
    private BigDecimal localhostLatitude;
    private BigDecimal localhostLongitude;
    private String address;
    private String shopName;
    private String phone;
    private String shopIntroduce;

    private List<String> shopImagList;

    private String categoryId;
    private String uid;

    /**
     * 划分的三级区域的id
     */
    private String areaDivide;
    private Integer uploadCommodityBalance;

    private String qrcode;
}
