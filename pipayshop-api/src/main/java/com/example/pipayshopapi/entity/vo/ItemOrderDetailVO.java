package com.example.pipayshopapi.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wzx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrderDetailVO {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    private String address;
    /**
     * pi的单价
     */
    private BigDecimal piPrice;
    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * pi钱包地址
     */
    private Integer piAddress;
    private String phone;

    private String buyerName;
    private String certificateImag;
}
