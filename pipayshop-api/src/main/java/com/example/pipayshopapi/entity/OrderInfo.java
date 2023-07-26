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
 * 订单数据表
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@TableName("order_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单数据的id
     */
    private String orderId;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

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
    private Integer orderStatus;

    /**
     * 0:未删除1：逻辑删除2：真实删除
     */
    private Integer delFlag;


}
