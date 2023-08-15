package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopOrderInfoVO {
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
    private String uId;
    /**
     * 卖家店铺id
     */
    private String shopId;


    /**
     * 商品id
     */
    private String commodityId;
    /**
     * 商店的商品名字
     */
    private String shopCommodityName;
    /**
     * 商店的商品第一张照片
     */
    private String firstPicture;


    /**
     * 用户id
     */
    private String uid;

    /**
     * 用户名
     */

    private String userName;
    /**
     * 用户头像
     */
    private String userImage;


    private Date createTime;
    private String imagsList;
    private String sellerName;


    /**
     * 订单的状态（0：待支付，1：已支付，2：已完成，3：无效订单）
     */
    private String orderStatus;
}
