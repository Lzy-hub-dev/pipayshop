package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private String shopInroduce;

    /**
     * 店铺图片的路径集合
     */
    private String shopImagList;

    /**
     * 店铺类别
     */
    private Integer categoryId;

    /**
     * 0:正常运用1:下架状态
     */
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public BigDecimal getLocalhostLatitude() {
        return localhostLatitude;
    }

    public void setLocalhostLatitude(BigDecimal localhostLatitude) {
        this.localhostLatitude = localhostLatitude;
    }
    public BigDecimal getLocalhostLongitude() {
        return localhostLongitude;
    }

    public void setLocalhostLongitude(BigDecimal localhostLongitude) {
        this.localhostLongitude = localhostLongitude;
    }
    public String getTagList() {
        return tagList;
    }

    public void setTagList(String tagList) {
        this.tagList = tagList;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
    public String getShopInroduce() {
        return shopInroduce;
    }

    public void setShopInroduce(String shopInroduce) {
        this.shopInroduce = shopInroduce;
    }
    public String getShopImagList() {
        return shopImagList;
    }

    public void setShopImagList(String shopImagList) {
        this.shopImagList = shopImagList;
    }
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ShopInfo{" +
            "id=" + id +
            ", shopId=" + shopId +
            ", shopName=" + shopName +
            ", localhostLatitude=" + localhostLatitude +
            ", localhostLongitude=" + localhostLongitude +
            ", tagList=" + tagList +
            ", address=" + address +
            ", score=" + score +
            ", shopInroduce=" + shopInroduce +
            ", shopImagList=" + shopImagList +
            ", categoryId=" + categoryId +
            ", status=" + status +
        "}";
    }
}
