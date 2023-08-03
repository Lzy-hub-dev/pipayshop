package com.example.pipayshopapi.entity.vo;

import com.example.pipayshopapi.entity.ItemOrderInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyItemOrderInfoVO {
    /**
     * 网店id
     */
    private String itemId;

    /**
     * 网店名称
     */
    private String itemName;
    /**
     * 用户头像
     */
    private String userImage;


    /**
     * 商品id
     */
    private String commodityId;
    /**
     * 商品名字
     */
    private String itemCommodityName;
    /**
     * 商品介绍
     */
    private String details;
    /**
     * 商品的展示图路径
     */
    private String avatarImag;


    /**
     * 订单id
     */
    private String orderId;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;
    /**
     * 下单用户id
     */
    private String uid;
    /**
     * 0:待支付1:已支付2：已完成3：无效订单
     */
    private Boolean orderStatus;
}
