package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 实体店分类
 * </p>
 *
 * @author mongdie
 * @since 2023-07-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCategoryVO<T> {
    /**
     * 所有实体店分类id
     */
    private String categoryId;
    /**
     * 所有实体店一级分类内容
     */
    private String content;

    /**
     * 所有实体店二级分类内容列表
     */
    List<T> CategoryVOList;

}
