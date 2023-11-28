package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwoZoneVO {
    String userId;
    //人员信息
    List<ZoneUserInfoVO> zoneUserInfoVOList;
}
