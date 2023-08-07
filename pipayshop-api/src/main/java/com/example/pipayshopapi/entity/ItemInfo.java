package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@TableName("item_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemInfo implements Serializable {


    private static final long serialVersionUID = 1L;
    @JsonIgnore
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 网店id
     */
    private String itemId;

    /**
     * 网店名称
     */
    private String itemName;

    /**
     * 是否会员(0映射为false，1映射为true)
     */
    private Boolean membership;
    /**
     * 网店的标签id集合
     */
    private String tagList;

    /**
     * 网店评分
     */
    private Double score;

    /**
     * 网店简介（广告词）
     */
    private String itemIntroduce;

    /**
     * 网店图片的路径集合
     */
    private String itemImagList;

    /**
     * 网店类别
     */
    private Integer categoryId;

    /**
     * 网店的所有者的用户id
     */
    private String uid;

    /**
     * 0:正常运用 1: 审核状态 2:下架状态
     */
    private Integer status;

    /**
     * 用户头像
     */
    private String userImage;

    /**
     * 商品上架剩余数
     */
    private Integer uploadBalance;


}
