package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradinOrderListVO {
    private String orderId;
    private String sellerId;
    private Integer typeId;
    private BigDecimal piBalance;
    private BigDecimal pointBalance;
    private String userName;
    private String userImage;
    private Integer status;
}
