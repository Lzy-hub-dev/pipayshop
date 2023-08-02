package com.example.pipayshopapi.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityPageVO {

    private Integer limit;
    private Integer page;

    private Integer secondCategoryId;

}
