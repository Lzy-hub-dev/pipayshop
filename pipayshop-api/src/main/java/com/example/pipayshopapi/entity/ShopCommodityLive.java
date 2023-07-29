package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }
    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getTagList() {
        return tagList;
    }

    public void setTagList(String tagList) {
        this.tagList = tagList;
    }
    public String getImageList() {
        return imageList;
    }

    public void setImageList(String imageList) {
        this.imageList = imageList;
    }
    public Integer getLand() {
        return land;
    }

    public void setLand(Integer land) {
        this.land = land;
    }
    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }
    public Integer getRestRoom() {
        return restRoom;
    }

    public void setRestRoom(Integer restRoom) {
        this.restRoom = restRoom;
    }
    public Integer getBed() {
        return bed;
    }

    public void setBed(Integer bed) {
        this.bed = bed;
    }
    public Integer getAdult() {
        return adult;
    }

    public void setAdult(Integer adult) {
        this.adult = adult;
    }
    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }
    public String getRestricted() {
        return restricted;
    }

    public void setRestricted(String restricted) {
        this.restricted = restricted;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public String getBasics() {
        return basics;
    }

    public void setBasics(String basics) {
        this.basics = basics;
    }
    public String getBath() {
        return bath;
    }

    public void setBath(String bath) {
        this.bath = bath;
    }
    public String getAppliance() {
        return appliance;
    }

    public void setAppliance(String appliance) {
        this.appliance = appliance;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
    public Boolean getRecommended() {
        return recommended;
    }

    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }

    @Override
    public String toString() {
        return "ShopCommodityLive{" +
            "id=" + id +
            ", commodityId=" + commodityId +
            ", shopId=" + shopId +
            ", detail=" + detail +
            ", tagList=" + tagList +
            ", imageList=" + imageList +
            ", land=" + land +
            ", room=" + room +
            ", restRoom=" + restRoom +
            ", bed=" + bed +
            ", adult=" + adult +
            ", children=" + children +
            ", restricted=" + restricted +
            ", checkInTime=" + checkInTime +
            ", departureTime=" + departureTime +
            ", basics=" + basics +
            ", bath=" + bath +
            ", appliance=" + appliance +
            ", price=" + price +
            ", delFlag=" + delFlag +
            ", recommended=" + recommended +
        "}";
    }
}
