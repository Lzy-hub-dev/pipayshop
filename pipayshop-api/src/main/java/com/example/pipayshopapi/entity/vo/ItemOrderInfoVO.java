package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrderInfoVO {
    private String orderId;
    private BigDecimal transactionAmount;
    private String commodityId;
    private String uId;
    private String shopId;
    private Date createTime;
    private String imagsList;
    private String itemCommodityName;
}
