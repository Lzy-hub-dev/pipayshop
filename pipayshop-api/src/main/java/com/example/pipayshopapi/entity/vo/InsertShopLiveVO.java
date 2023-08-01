package com.example.pipayshopapi.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertShopLiveVO {
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
     * 服务的头像
     */
    private String avatarImag;
}
