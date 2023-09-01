package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 实体店二级分类表
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@TableName("shop_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCategory implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所有实体店二级分类id
     */
    private String categoryId;

    /**
     * 所有实体店二级分类内容
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
    private String categoryPid;

    /**
     * 0:启用1:禁用
     */
    private Boolean delFlag;
    /**
     * 分类图片
     */
    private String categoryImg;

}
