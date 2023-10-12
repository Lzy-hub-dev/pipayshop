package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayPalDTO {
    private String userId;
    private BigDecimal buyPointBalance;
    private BigDecimal payPalMoney;
}
