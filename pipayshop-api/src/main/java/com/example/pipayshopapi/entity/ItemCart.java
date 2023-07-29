package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author zxb
 * @since 2023-07-28
 */
@TableName("item_cart")
public class ItemCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 购物车id
     */
    private String cartId;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ItemCart{" +
            "id=" + id +
            ", userId=" + userId +
            ", commodityId=" + commodityId +
            ", createTime=" + createTime +
        "}";
    }
}