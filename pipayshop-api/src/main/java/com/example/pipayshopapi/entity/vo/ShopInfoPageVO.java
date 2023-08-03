package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopInfoPageVO {

    private Integer limit;
    private Integer page;

    private String shopName;
    private String categoryId;
    private String address;


}
