package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商户订单数据表
 * </p>
 *
 * @author zxb
 * @since 2023-07-28
 */
@TableName("item_order_info")
public class ItemOrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 下单用户id
     */
    private String uid;

    /**
     * 卖家id
     */
    private String shopId;

    /**
     * 0:待支付1:已支付2：已完成3：无效订单
     */
    private Boolean orderStatus;

    /**
     * 0:未删除1：逻辑删除2：真实删除
     */
    private Boolean delFlag;

    /**
     * 下单时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
    public Boolean getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
    }
    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
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

    @Override
    public String toString() {
        return "ItemOrderInfo{" +
            "id=" + id +
            ", orderId=" + orderId +
            ", transactionAmount=" + transactionAmount +
            ", commodityId=" + commodityId +
            ", uid=" + uid +
            ", shopId=" + shopId +
            ", orderStatus=" + orderStatus +
            ", delFlag=" + delFlag +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
