package com.example.pipayshopapi.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;

    private String shopIntroduce;
}
