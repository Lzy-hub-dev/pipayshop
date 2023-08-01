package com.example.pipayshopapi.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 实体店的信息
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@TableName("shop_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 店铺id
     */
    private String shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 坐标维度
     */
    private BigDecimal localhostLatitude;

    /**
     * 坐标经度
     */
    private BigDecimal localhostLongitude;

    /**
     * 店铺的标签id集合
     */

    private String tagList;

    /**
     * 联系电话
     */
    private String phone;
    /**
     * 店铺地址
     */
    private String address;

    /**
     * 店铺评分
     */
    private Double score;

    /**
     * 店铺简介（广告词）
     */
    private String shopIntroduce;

    /**
     * 店铺图片的路径集合
     */
    private String shopImagList;

    /**
     * 实体店的头像
     */
    private String userImage;

    /**
     * 店铺类别
     */
    private Integer categoryId;

    /**
     * 店铺的所有者的用户id
     */
    private String uid;

    /**
     * 0:正常运用1:下架状态
     */
    private Boolean status;

}
