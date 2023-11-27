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
    //所属用户ID
    String userId;
    //团ID
    Long zoneId;
    //团内人数
    Integer zoneUserNum;
    //邀请码
    String invitationCode;
    //人员信息
    List<ZoneUserInfoVO> zoneUserInfoVOList;
}
