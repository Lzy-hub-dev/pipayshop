package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: jiangjiafeng
 * @ClassName ShopDTO
 * @Description 实体店DTO
 * @date 2023/7/26 16:54
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO extends PageDTO {
    /**
     * 标签id
     */
    private Integer tagId;
    /**
     * 一级分类id
     */
    private Integer categoryTopId;
    /**
     * 二级分类id
     */
    private Integer categorySecId;
    /**
     * 维度
     */
    private BigDecimal latitude;
    /**
     * 经度
     */
    private BigDecimal longitude;
}
