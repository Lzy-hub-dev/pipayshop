package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("user_by_zone")
public class UserByZone implements Serializable {
    private static final long serialVersionUID = 1L;

    //表id
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    //用户ID
    String userId;
    //专区消费金额数（超过门槛就有成为团长资格）
    BigDecimal zoneConsumptionSum;
    //开团时用户门槛
    BigDecimal userThreshold;
    //邀请人用户ID
    String superiorUserId;
    //是否为团长（0：否 1：是）
    Integer isGroupLeader;
    //是否入团（0没有1有）
    Integer joinZone;
    //是否具有再开团资格(0：不具有 1：具有)
    Integer openingGroupQualification;
    //团数量
    Integer zoneNum;
    //当前拥有的最新的团id
    Long newZoneId;
    //当前拥有的最新的二级团id
    Long newTwoZoneId;
    //获取一级佣金次数
    Integer firstRebateNum;
    //获取二级佣金次数
    Integer twoRebateNum;

}
