package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RechargeVO {

    private String uid;

    private String shopId;

    private Integer permissionsCount;

    private Integer transactionAmount;
}
