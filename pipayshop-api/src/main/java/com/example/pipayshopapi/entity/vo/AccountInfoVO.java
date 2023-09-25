package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author mongdie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfoVO {
    /**
     * 积分余额
     */
    private BigDecimal pointBalance;

    /**
     * 可用积分
     */
    private BigDecimal availableBalance;

    /**
     * 冻结积分
     */
    private BigDecimal frozenBalance;

    /**
     * pi币余额
     */
    private BigDecimal piBalance;
}
