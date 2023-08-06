package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * fileName: RechargePermissionsOrderVO
 * author: 四面神
 * createTime:2023/8/6 19:11
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RechargePermissionsOrderVO {

    /**
     * 用户id
     */
    private String uid;

    /**
     * 交易的权限数量
     */
    private Integer permissionsCount;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;
}
