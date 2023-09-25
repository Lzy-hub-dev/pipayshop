package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wzx
 * @since 2023-09-19
 */
@TableName("tradin_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradinOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 卖家id
     */
    private String sellerId;

    /**
     * 买家id
     */
    private String buyerId;

    /**
     * 0:换积分 1:换pi币
     */
    private Integer typeId;

    /**
     * 交换pi币
     */
    private BigDecimal piBalance;

    /**
     * 交换积分
     */
    private BigDecimal pointBalance;

    /**
     * 图片url
     */
    private String piAddress;

    /**
     * 图片url
     */
    private String imageUrl;
    /**
     * 日志id外键
     */
    private String journalId;
    /**
     *0:开始交易 1:提交积分/地址 2:提交凭证 3:交易完成
    **/
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public TradinOrder(String orderId, String sellerId, String buyerId, Integer typeId, BigDecimal piBalance, BigDecimal pointBalance, String piAddress, Integer status) {
        this.orderId = orderId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.typeId = typeId;
        this.piBalance = piBalance;
        this.pointBalance = pointBalance;
        this.piAddress = piAddress;
        this.status = status;
    }

    public TradinOrder(String orderId, String sellerId, String buyerId, Integer typeId, BigDecimal piBalance, BigDecimal pointBalance) {
        this.orderId = orderId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.typeId = typeId;
        this.piBalance = piBalance;
        this.pointBalance = pointBalance;
    }
}
