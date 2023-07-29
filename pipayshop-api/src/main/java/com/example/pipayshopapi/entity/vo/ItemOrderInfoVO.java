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
    private String itemId;
    private Date createTime;
    private String imagsList;
    private String itemCommodityName;
    private String firstPicture;
    private String sellerName;
    private String orderStatus;
}
