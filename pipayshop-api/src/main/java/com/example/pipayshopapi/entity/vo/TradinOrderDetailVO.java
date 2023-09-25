package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradinOrderDetailVO {
    private String orderId;
    private String sellerId;
    private String sellerName;
    private String sellerImageUrl;
    private String buyerId;
    private String buyerName;
    private String buyerImageUrl;
    private Integer typeId;
    private BigDecimal piBalance;
    private BigDecimal pointBalance;
    private String piAddress;
    private String imageUrl;
    private Integer status;

}
