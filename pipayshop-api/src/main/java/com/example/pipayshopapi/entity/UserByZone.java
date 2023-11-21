package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
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
    BigInteger zoneConsumptionSum;
    //邀请人用户ID
    String superiorUserId;
    //是否为团长（0：否 1：是）
    Integer isGroupLeader;
    //是否具有再开团资格(0：不具有 1：具有)
    Integer openingGroupQualification;
    //团数量
    Integer zoneNum;
    //获取一级佣金次数
    Integer firstCommissionNum;
    //获取二级佣金次数
    Integer twoCommissionNum;
    //邀请码
    String invitationCode;
}
