package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplyShopCommodityDTO {

    private String commodityName;

    private List<String> commodityImgList;

    private  String commodityDetail;

    private BigDecimal price;

    private String shopId;

    private Integer validityTime;

    private Integer residue;

    private String reservationInformation;
}
