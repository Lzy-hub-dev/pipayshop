package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneUserInfoVO {

    String userId;
    String userName;
    /**
     * 用户头像
     */
    private String userImage;

    Long zoneId;
    //消费金额
    BigDecimal zoneConsumptionSum;
}
