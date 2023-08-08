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

/**
 * <p>
 *
 * </p>
 *
 * @author zxb
 * @since 2023-08-08
 */
@TableName("transaction_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 交易id
     */
    private String transactionId;

    /**
     * 商家id
     */
    private String shopId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;

    /**
     * 交易时间
     */
    private LocalDateTime createTime;

    /**
     * 0:正常1：逻辑删除
     */
    private Integer delFlag;


}
