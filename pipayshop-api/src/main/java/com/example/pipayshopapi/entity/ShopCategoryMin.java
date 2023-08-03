package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体店三级分类表
 * @author wzx
 */
@TableName("shop_category_min")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCategoryMin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 所有实体店三级分类id
     */
    private Integer categoryId;

    /**
     * 所有实体店三级分类内容
     */
    private String content;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 该分类的父级分类id
     */
    private Integer categoryPid;

    /**
     * 0:启用1:禁用
     */
    private Boolean delFlag;
    /**
     * 分类图片
     */
    private String categoryImg;
}