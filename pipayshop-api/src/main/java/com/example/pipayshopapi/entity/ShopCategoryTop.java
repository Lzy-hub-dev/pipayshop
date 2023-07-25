package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 实体店一级分类表
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@TableName("shop_category_top")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCategoryTop implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 所有实体店首级分类id
     */
    private Integer categoryId;

    /**
     * 所有实体店首级分类内容
     */
    private String content;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 0:启用1:禁用
     */
    private Boolean delFlag;


}
