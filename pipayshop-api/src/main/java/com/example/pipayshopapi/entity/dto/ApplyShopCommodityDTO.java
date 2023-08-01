package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.Bidi;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplyShopCommodityDTO {
    private String commodityName;
    private  String commodityDetail;
    private BigDecimal price;
    private Integer categoryTopId;
    private Integer categoryId;
    private String shopId;
    private Integer residue;
    private String reservationInformation;
    private String myEvaluate;
    private String avatarImag;
}
