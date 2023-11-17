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
 *
 * </p>
 *
 * @author wzx
 * @since 2023-09-05
 */
@TableName("item_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 交易的总金额
     */
    private BigDecimal transactionAmount;

    /**
     * 改价/折扣后的总金额
     */
    private BigDecimal discount;

    /**
     * 卖家店铺id
     */
    private String itemId;

    /**
     * 买家用户id
     */
    private String uid;

    /**
     * 买家的收货地址数据id
     */
    private String buyerDataId;



    /**
     * 0:待支付1:已支付2：已完成3：无效订单4:已评价
     */
    private Integer orderStatus;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    /**
     * 0:未删除1：逻辑删除
     */
    private Integer delFlag;

    /**
     * 支付id
     */
    private Integer paymentId;
    /**
     * pi支付凭证
     */
    private String certificateImag;

    /**
     * pi总金额
     */
    private BigDecimal piAmount;

    public ItemOrder(Long id, String orderId, BigDecimal transactionAmount, BigDecimal discount, String itemId, String uid, String buyerDataId, Integer orderStatus, Date createTime, Date updateTime, Date orderTime, Integer delFlag) {
        this.id = id;
        this.orderId = orderId;
        this.transactionAmount = transactionAmount;
        this.discount = discount;
        this.itemId = itemId;
        this.uid = uid;
        this.buyerDataId = buyerDataId;
        this.orderStatus = orderStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.orderTime = orderTime;
        this.delFlag = delFlag;
    }
}
