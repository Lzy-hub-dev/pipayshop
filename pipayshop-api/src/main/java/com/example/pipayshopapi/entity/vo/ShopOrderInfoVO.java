package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: jiangjiafeng
 * @ClassName ShopOrderInfoVO
 * @Description TODO
 * @date 2023/7/29 15:30
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopOrderInfoVO {
    private String orderId;
    private BigDecimal transactionAmount;
    private String commodityId;
    private String uId;
    private String shopId;
    private Date createTime;
    private String imagsList;
    private String shopCommodityName;
    private String firstPicture;
    private String sellerName;
    private String orderStatus;
}
