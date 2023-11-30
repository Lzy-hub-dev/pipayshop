package com.example.pipayshopapi.entity.vo;

import com.example.pipayshopapi.entity.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirstZoneVO {
    //团内人数
    Integer zoneUserNum;
    //邀请码
    String invitationCode;
    //返利资格
    Integer rebateQualification;
    //是否失效
    Integer invalid;
    //是否返佣
    Integer levelRebate;
    //人员信息
    List<ZoneUserInfoVO> zoneUserInfoVOList;
}
