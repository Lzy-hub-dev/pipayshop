package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.FirstZone;
import com.example.pipayshopapi.entity.TwoZone;
import com.example.pipayshopapi.entity.dto.UserByZoneInvitationCodeDTO;
import com.example.pipayshopapi.entity.vo.FirstZoneVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.entity.vo.TwoZoneVO;
import com.example.pipayshopapi.entity.vo.ZoneStatusVO;

import java.util.List;

public interface ZoneService extends IService<FirstZone> {
    /**
     * 创建一、二级团
     * @param userId
     */
    ResponseVO<String> createZone(String userId);

    /**
     * 根据用户id查询用户拥有的团
     * @param userId
     * @return
     */
    List<FirstZone> selectZones(String userId);

    /**
     * 入团
     * @param userByZoneInvitationCodeDTO
     * @return
     */
    ResponseVO<String> joinZone(UserByZoneInvitationCodeDTO userByZoneInvitationCodeDTO);

    /**
     * 查询用户二级团信息
     * @param userId
     * @param zoneId
     */
    List<TwoZoneVO> selectTwoZones(String userId, Long zoneId);

    FirstZoneVO selectZone(String userId, Long firstZoneId);

    ZoneStatusVO selectTwoZone(String userId, Long firstZoneId);
}
