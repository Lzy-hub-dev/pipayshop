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
 * 实体店住的服务表
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@TableName("shop_commodity_live")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCommodityLive implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 服务id
     */
    private String commodityId;

    /**
     * 实体店id
     */
    private String shopId;

    /**
     * 服务描述
     */
    private String detail;

    /**
     * 服务标签集合
     */
    private String tagList;

    /**
     * 服务图片集合
     */
    private String imageList;

    /**
     * 出租土地平方米
     */
    private Integer land;

    /**
     * 几间房
     */
    private Integer room;

    /**
     * 几间卫生间
     */
    private Integer restRoom;

    /**
     * 几个床
     */
    private Integer bed;

    /**
     * 能住几个成年人
     */
    private Integer adult;

    /**
     * 能住几个儿童
     */
    private Integer children;

    /**
     * 限制
     */
    private String restricted;

    /**
     * 入住时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkInTime;

    /**
     * 离开时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date departureTime;

    /**
     * 基础设施
     */
    private String basics;

    /**
     * 洗沐设施
     */
    private String bath;

    /**
     * 电器设施
     */
    private String appliance;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 服务状态 0:未删除 1：逻辑删除 2：物理删除
     */
    private Integer delFlag;

    /**
     * 推荐服务 0:不推荐 1：推荐
     */
    private Boolean recommended;

    /**
     * 服务的头像
     */
    private String avatarImag;


}
