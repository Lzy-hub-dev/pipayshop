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
public class FrontAccountInfoVO {
    /**
     * 变化的积分余额
     */
    private BigDecimal PointBalance;

    /**
     * 变化的pi币
     */
    private BigDecimal PiBalance;

}
