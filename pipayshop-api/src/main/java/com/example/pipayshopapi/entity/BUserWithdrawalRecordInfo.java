package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.ws.soap.Addressing;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 商户提现记录
 * </p>
 *
 * @author zxb
 * @since 2023-08-28
 */
@TableName("b_user_withdrawal_record_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BUserWithdrawalRecordInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 提现所需积分
     */
    private BigDecimal withdrawalPoint;

    /**
     * 提现时间
     */
    private Date createTime;


    public BUserWithdrawalRecordInfo(String uid, BigDecimal withdrawalPoint) {
        this.uid = uid;
        this.withdrawalPoint = withdrawalPoint;
    }
}
