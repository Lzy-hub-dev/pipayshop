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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

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
    private String itemId;

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
    private String itemName;

    /**
     * 上架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 下架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deleteTime;

    /**
     * 商品颜色集合
     */
    private String itemColorList;

    /**
     * 商品尺寸集合
     */
    private String itemSizeList;

    /**
     * 发货地
     */
    private String originAddress;

    /**
     * 支持发货的地区集合
     */
    private String acceptAddressList;

    /**
     * 商家id（商家级别）
     */
    private String sellerId;

    /**
     * 商品介绍
     */
    private String itemDetails;

    /**
     * 商品图片的地址集合
     */
    private String itemImagsList;

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
     * 0:正常 1:逻辑删除 2:真正删除
     */
    private Boolean delFlag;


}
