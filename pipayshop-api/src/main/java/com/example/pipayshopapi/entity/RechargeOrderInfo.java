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
import java.util.Date;

/**
 * <p>
 * 商户订单数据表
 * </p>
 *
 * @author zxb
 * @since 2023-08-08
 */
@TableName("recharge_order_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RechargeOrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 下单用户id
     */
    private String uid;

    /**
     * 增加的积分
     */
    private BigDecimal pointAmount;

    /**
     * 交易的pi币
     */
    private BigDecimal piSum;

    /**
     * 0:待支付1:已支付2：已完成3：无效订单4：已评论订单
     */
    private Integer orderStatus;

    /**
     * 下单时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 0:未删除1：逻辑删除2：真实删除
     */
    private Integer delFlag;


}
