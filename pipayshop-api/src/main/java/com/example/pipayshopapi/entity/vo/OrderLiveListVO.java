package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLiveListVO {
    /**
     * 订单id
     */
    private String orderId;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 商品的id
     */
    private String commodityId;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;

    /**
     * 房型的头像
     */
    private String avatarImag;

    /**
     * 房型的名字
     */
    private String roomTypeName;


    /**
     *酒店的名字
     */
    private String shopName;

    /**
     *酒店的头像
     */
    private String userImage;

    /**
     * 酒店的地址
     */
    private String address;
}
