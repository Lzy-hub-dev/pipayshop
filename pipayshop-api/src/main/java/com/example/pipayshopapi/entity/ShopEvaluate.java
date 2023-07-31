package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 网店评价
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@TableName("shop_evaluate")
public class ShopEvaluate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 实体店id
     */
    private String itemId;

    /**
     * 评价id
     */
    private String evaluateId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 服务id
     */
    private String commodityId;

    /**
     * 评价
     */
    private String evaluate;

    /**
     * 评价时间
     */
    private LocalDateTime createTime;

    /**
     * 评价状态(0发布 1取消)
     */
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public String getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(String evaluateId) {
        this.evaluateId = evaluateId;
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
    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ShopEvaluate{" +
            "id=" + id +
            ", itemId=" + itemId +
            ", evaluateId=" + evaluateId +
            ", userId=" + userId +
            ", commodityId=" + commodityId +
            ", evaluate=" + evaluate +
            ", createTime=" + createTime +
            ", status=" + status +
        "}";
    }
}
