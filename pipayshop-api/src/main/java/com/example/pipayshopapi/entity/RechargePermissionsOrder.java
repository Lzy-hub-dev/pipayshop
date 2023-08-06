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
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zxb
 * @since 2023-08-06
 */
@TableName("recharge_permissions_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RechargePermissionsOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 充值权限的订单id
     */
    private String orderId;

    /**
     * 交易的用户id
     */
    private String uid;

    /**
     * 交易的权限数量
     */
    private Integer permissionsCount;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 0:正常1:逻辑删除
     */
    private Integer delFlag;

    /**
     *0：未支付1：已支付
     */
    private Integer status;
    /**
     * 充值类别0:网店相关1:实体店相关
     */
    private Integer chargeType;
}
