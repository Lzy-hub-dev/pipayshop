package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author wzx
 * 订单列表展示的数据传输实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListVO {
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
     * 商品的描述
     */
    private String details;

    /**
     * 卖家的头像url地址
     */
    private String sellerImg;

    /**
     * 卖家店铺名称
     */
    private String sellerName;

    /**
     * 商品图的url地址
     */
    private String commodityImg;
    /**
     *商品的规格
     */
    private String commoditySpecification;

}
