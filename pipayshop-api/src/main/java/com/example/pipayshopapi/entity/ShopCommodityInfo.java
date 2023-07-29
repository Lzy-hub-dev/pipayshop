package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 实体店的商品表
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@TableName("shop_commodity_info")
public class ShopCommodityInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 店铺商品的id
     */
    private String commodityId;

    /**
     * 商品的名字
     */
    private String commodityName;

    /**
     * 商品的图片路径集合
     */
    private String commodityImgList;

    /**
     * 商品详情
     */
    private String commodityDetail;

    /**
     * 商品的价格
     */
    private BigDecimal price;

    /**
     * 商品的月收量
     */
    private Integer monthlySales;

    /**
     * 该商品的店铺id
     */
    private String shopId;

    /**
     * 商品上架的时间
     */
    private LocalDateTime createTime;

    /**
     * 商品数据修改的时间
     */
    private LocalDateTime updateTime;

    /**
     * 商品有效期
     */
    private Integer validityTime;

    /**
     * 商品剩余数量
     */
    private Integer residue;

    /**
     * 预定的注意事项
     */
    private String reservationInformation;

    /**
     * 0:正常1:下架2:绝对删除
     */
    private Boolean status;

    /**
     * 我的评价
     */
    private String myEvaluate;

    public String getMyEvaluate() {
        return myEvaluate;
    }

    public void setMyEvaluate(String myEvaluate) {
        this.myEvaluate = myEvaluate;
    }

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
    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }
    public String getCommodityImgList() {
        return commodityImgList;
    }

    public void setCommodityImgList(String commodityImgList) {
        this.commodityImgList = commodityImgList;
    }
    public String getCommodityDetail() {
        return commodityDetail;
    }

    public void setCommodityDetail(String commodityDetail) {
        this.commodityDetail = commodityDetail;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public Integer getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(Integer monthlySales) {
        this.monthlySales = monthlySales;
    }
    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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
    public Integer getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(Integer validityTime) {
        this.validityTime = validityTime;
    }
    public Integer getResidue() {
        return residue;
    }

    public void setResidue(Integer residue) {
        this.residue = residue;
    }
    public String getReservationInformation() {
        return reservationInformation;
    }

    public void setReservationInformation(String reservationInformation) {
        this.reservationInformation = reservationInformation;
    }
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ShopCommodityInfo{" +
            "id=" + id +
            ", commodityId=" + commodityId +
            ", commodityName=" + commodityName +
            ", commodityImgList=" + commodityImgList +
            ", commodityDetail=" + commodityDetail +
            ", price=" + price +
            ", monthlySales=" + monthlySales +
            ", shopId=" + shopId +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", validityTime=" + validityTime +
            ", residue=" + residue +
            ", reservationInformation=" + reservationInformation +
            ", status=" + status +
        "}";
    }
}
