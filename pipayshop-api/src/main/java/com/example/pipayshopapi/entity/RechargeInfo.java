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
 * 
 * </p>
 *
 * @author zxb
 * @since 2023-08-08
 */
@TableName("recharge_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RechargeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 交易的pi币数量
     */
    private BigDecimal piNum;

    /**
     * 交易前的PI余额
     */
    private BigDecimal prePi;

    /**
     * 交易后的PI余额
     */
    private BigDecimal lastPi;

    /**
     * 交易的积分数量
     */
    private BigDecimal pointSum;

    /**
     * 交易前的积分数量
     */
    private BigDecimal prePoint;

    /**
     * 交易后的积分数量
     */
    private BigDecimal lastPoint;

    /**
     * 充值时间
     */
    private Date createTime;

    /**
     * 0:正常1：逻辑删除
     */
    private Boolean delFlag;


}
