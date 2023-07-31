package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 网店的商品表
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@TableName("item_commodity_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCommodityInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 品牌id 可有可无
     */
    private Integer brandId;

    /**
     * 商品原价
     */
    private BigDecimal originPrice;

    /**
     * 商品现价
     */
    private BigDecimal price;

    /**
     * 商品名字
     */
    private String itemCommodityName;

    /**
     * 上架时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 下架时间
     */
    private LocalDateTime deleteTime;

    /**
     * 商品颜色集合
     */
    private String colorList;

    /**
     * 商品尺寸集合
     */
    private String sizeList;

    /**
     * 发货地
     */
    private String originAddress;

    /**
     * 支持发货的地区集合
     */
    private String acceptAddressList;

    /**
     * 网店id
     */
    private String itemId;

    /**
     * 商品介绍
     */
    private String details;

    /**
     * 商品图片的地址集合
     */
    private String imagsList;

    /**
     * 库存
     */
    private Integer inventory;

    /**
     * 免运费的起始订购数量
     */
    private Integer freeShippingNum;

    /**
     * 商品类别(外键关联)
     */
    private Integer categoryId;

    /**
     * 优惠卷的集合
     */
    private String couponsList;

    /**
     * 商品的标签id集合
     */
    private String tagList;

    /**
     * 0:审核 1:上架 2:下架3:绝对删除
     */
    private Integer status;


    /**
     * 商品的折损（枚举）
     */
    private  Integer degreeLoss;


}
