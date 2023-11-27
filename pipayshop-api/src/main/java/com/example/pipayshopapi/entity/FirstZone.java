package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("first_zone")
public class FirstZone implements Serializable {
    private static final long serialVersionUID = 1L;

    //表ID
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    //所属用户ID
    String userId;
    //团ID
    Long zoneId;
    //团名称
    String zoneName;
    //团内人数
    Integer zoneUserNum;
    //返利资格
    Integer rebateQualification;
    //是否返利
    Integer levelRebate;
    //邀请码
    String invitationCode;
    //开团时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date openingZoneTime;
    //团失效时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date endTime;
    //是否失效
    Integer invalid;
}
