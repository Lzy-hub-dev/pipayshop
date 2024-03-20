package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCommodityLiveInfoListDTO1 {
    /**
     * 所属实体店id
     */
    private String shopId;
    /**
     * 是否开启价格查询
     */
    private boolean priceSearch;
    /**
     * 是否升序
     */
    private boolean ascending;
    /**
     * 降序
     */
    private boolean descending;
    /**
     * 0上架  1下架
     */
    private String status;
}
