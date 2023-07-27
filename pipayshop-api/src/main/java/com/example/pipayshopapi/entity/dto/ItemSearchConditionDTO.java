package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: zxb
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSearchConditionDTO {

    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 最小价格
     */
    private BigDecimal minPrice;

    /**
     * 最大价格
     */
    private BigDecimal maxPrice;

    /**
     * 折损率 枚举类
     */
    private Integer degreeLoss;

    /**
     * 当前页
     */
    private Integer page;


    /**
     * 一页多少条数据
     */
    private Integer limit;
}
