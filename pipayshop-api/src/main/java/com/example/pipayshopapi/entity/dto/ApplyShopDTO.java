package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    private Integer categoryId;
    private String uid;
}
