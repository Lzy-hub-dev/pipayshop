package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jiangjiafeng
 * @ClassName CategoryVO
 * @Description 分类VO
 * @date 2023/7/26 12:27
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCategoryVO {
    /**
     * 实体店分类id
     */
    private Integer categoryId;
    /**
     * 实体店分类内容
     */
    private String content;
    /**
     * 该分类的父级分类id
     */
    private Integer categoryPid;
}
