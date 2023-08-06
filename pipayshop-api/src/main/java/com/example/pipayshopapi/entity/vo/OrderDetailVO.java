package com.example.pipayshopapi.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.List;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wzx
 * 已支付订单详情界面的数据封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVO {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    private String orderId;

    private String address;

    private String phone;

    private String buyerName;

    private BigDecimal transactionAmount;

    private Integer number;

    /**
     * 商品图像
     */
    private String avatarImag;

    /**
     * 商品介绍
     */
    private String details;

    /**
     * 卖家头像
     */
    private String userImage;

}
