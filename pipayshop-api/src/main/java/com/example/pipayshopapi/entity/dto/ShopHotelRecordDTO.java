package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ThinkPad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopHotelRecordDTO {
    private String roomId;
    private String name;
    private String phone;
    private Date startTime;
    private Date endTime;
    private BigDecimal transactionAmount;
    private String commodityId;
    private String uid;
    private String shopId;

}
