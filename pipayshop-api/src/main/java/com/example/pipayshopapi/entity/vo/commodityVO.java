package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class commodityVO {


    private String commodityId;
    private String itemCommodityName;
    private String originPrice;
    private String price;
    private String details;
    private String imagsList;

}
