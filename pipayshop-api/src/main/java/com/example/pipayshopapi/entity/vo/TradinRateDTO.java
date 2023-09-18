package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradinRateDTO {
    /**
     * 转化货币
     */
    private String title;

    /**
     * 单位
     */
    private String format;

    /**
     * 汇率
     */
    private BigDecimal conversionRate;
}
