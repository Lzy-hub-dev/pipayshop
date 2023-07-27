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
     * 0:正常 1:逻辑删除 2:真正删除
     */
    private Boolean delFlag;


    /**
     * 商品的折损（枚举）
     */
    private  Integer degreeLoss;

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
    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getItemCommodityName() {
        return itemCommodityName;
    }

    public void setItemCommodityName(String itemCommodityName) {
        this.itemCommodityName = itemCommodityName;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public LocalDateTime getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(LocalDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }
    public String getColorList() {
        return colorList;
    }

    public void setColorList(String colorList) {
        this.colorList = colorList;
    }
    public String getSizeList() {
        return sizeList;
    }

    public void setSizeList(String sizeList) {
        this.sizeList = sizeList;
    }
    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }
    public String getAcceptAddressList() {
        return acceptAddressList;
    }

    public void setAcceptAddressList(String acceptAddressList) {
        this.acceptAddressList = acceptAddressList;
    }
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    public String getImagsList() {
        return imagsList;
    }

    public void setImagsList(String imagsList) {
        this.imagsList = imagsList;
    }
    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }
    public Integer getFreeShippingNum() {
        return freeShippingNum;
    }

    public void setFreeShippingNum(Integer freeShippingNum) {
        this.freeShippingNum = freeShippingNum;
    }
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public String getCouponsList() {
        return couponsList;
    }

    public void setCouponsList(String couponsList) {
        this.couponsList = couponsList;
    }
    public String getTagList() {
        return tagList;
    }

    public void setTagList(String tagList) {
        this.tagList = tagList;
    }
    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "ItemCommodityInfo{" +
            "id=" + id +
            ", commodityId=" + commodityId +
            ", originPrice=" + originPrice +
            ", price=" + price +
            ", itemCommodityName=" + itemCommodityName +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", deleteTime=" + deleteTime +
            ", colorList=" + colorList +
            ", sizeList=" + sizeList +
            ", originAddress=" + originAddress +
            ", acceptAddressList=" + acceptAddressList +
            ", itemId=" + itemId +
            ", details=" + details +
            ", imagsList=" + imagsList +
            ", inventory=" + inventory +
            ", freeShippingNum=" + freeShippingNum +
            ", categoryId=" + categoryId +
            ", couponsList=" + couponsList +
            ", tagList=" + tagList +
            ", delFlag=" + delFlag +
        "}";
    }
}
