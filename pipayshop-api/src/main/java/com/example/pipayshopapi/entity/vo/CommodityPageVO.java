package com.example.pipayshopapi.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wzx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityPageVO {

    private Integer limit;
    private Integer page;
    private Integer secondCategoryId;
    /**
     * 用户所在的国家编码
     */
    private String countryCode;
}
