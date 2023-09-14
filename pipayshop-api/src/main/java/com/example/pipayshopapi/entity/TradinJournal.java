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
 * @author wzx
 * @since 2023-09-12
 */
@TableName("tradin_journal")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradinJournal implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 日志id
     */
    private String journalId;

    /**
     * 发布者
     */
    private String publisherUid;

    /**
     * 交易者
     */
    private String traderUid;

    /**
     * 交易类型0：换积分 1：换pi币
     */
    private Integer typeId;

    /**
     * 交易积分
     */
    private BigDecimal pointBalance;

    /**
     * 交易pi币
     */
    private BigDecimal piBalance;

    /**
     * 发布者交易前积分
     */
    private BigDecimal publisherPointBalanceBefore;

    /**
     * 发布者交易后积分
     */
    private BigDecimal publisherPointBalanceAfter;

    /**
     * 发布者交易前pi币
     */
    private BigDecimal publisherPiBalanceBofore;

    /**
     * 发布者交易后pi币
     */
    private BigDecimal publisherPiBalanceAfter;

    /**
     * 交易者交易前积分
     */
    private BigDecimal traderPointBalanceBefore;

    /**
     * 交易者交易后积分
     */
    private BigDecimal traderPointBalanceAfter;

    /**
     * 交易者交易前pi币
     */
    private BigDecimal traderPiBalanceBofore;

    /**
     * 交易者交易后pi币
     */
    private BigDecimal traderPiBalanceAfter;

    /**
     * 交易时间
     */
    private Date createTime;

    public TradinJournal(String journalId, String publisherUid, Integer typeId, BigDecimal pointBalance, BigDecimal piBalance, BigDecimal publisherPointBalanceBefore, BigDecimal publisherPointBalanceAfter) {
        this.journalId = journalId;
        this.publisherUid = publisherUid;
        this.typeId = typeId;
        this.pointBalance = pointBalance;
        this.piBalance = piBalance;
        this.publisherPointBalanceBefore = publisherPointBalanceBefore;
        this.publisherPointBalanceAfter = publisherPointBalanceAfter;

    }

    public TradinJournal(String journalId, String publisherUid, Integer typeId, BigDecimal pointBalance, BigDecimal piBalance) {
        this.journalId = journalId;
        this.publisherUid = publisherUid;
        this.typeId = typeId;
        this.pointBalance = pointBalance;
        this.piBalance = piBalance;
    }
}
