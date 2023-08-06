package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author wzx
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
    /**
     * 充值类别0:网店相关1:实体店相关
     */
    private Integer chargeType;
}
