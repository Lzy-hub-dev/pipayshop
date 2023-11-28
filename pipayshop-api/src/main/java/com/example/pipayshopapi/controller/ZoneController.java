package com.example.pipayshopapi.controller;

import com.example.pipayshopapi.entity.FirstZone;
import com.example.pipayshopapi.entity.TwoZone;
import com.example.pipayshopapi.entity.dto.UserByZoneInvitationCodeDTO;
import com.example.pipayshopapi.entity.vo.FirstZoneVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.entity.vo.TwoZoneVO;
import com.example.pipayshopapi.entity.vo.ZoneStatusVO;
import com.example.pipayshopapi.service.ZoneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  团controller
 * </p>
 *
 * @author c_shunyi
 * @since 2023-11-22
 */
@RestController
@RequestMapping("/pipayshopapi/Zone")
@Api(tags = "团接口")
@Slf4j
public class ZoneController {

    @Autowired
    ZoneService zoneService;

    @ApiOperation("根据用户id创建一级、二级团")
    @PostMapping("createZones")
    public ResponseVO<String> createZone(@RequestBody Map<String, Object> requestBody){
        return zoneService.createZone(requestBody.get("userId").toString());

    }
    @ApiOperation("查询用户名和一级团id查询当前一级团信息")
    @GetMapping("selectZone/{userId}/{firstZoneId}")
    public ResponseVO<FirstZoneVO> selectZone(@PathVariable String userId, @PathVariable Long firstZoneId){
        if(zoneService.selectZone(userId, firstZoneId).getZoneUserNum() == null)
            return ResponseVO.getSuccessResponseMsg(null, "无该团信息");
        return ResponseVO.getSuccessResponseVo(zoneService.selectZone(userId, firstZoneId));
    }

    @ApiOperation("根据用户id查询用户拥有的所有一级团")
    @GetMapping("selectZones/{userId}")
    public ResponseVO<List<FirstZone>> selectZones(@PathVariable String userId){
        return ResponseVO.getSuccessResponseVo(zoneService.selectZones(userId));
    }

    @ApiOperation("查询用户名和一级团id查询所有二级团信息")
    @GetMapping("selectTwoZones/{userId}/{firstZoneId}")
    public ResponseVO<List<TwoZoneVO>> selectTwoZones(@PathVariable String userId, @PathVariable Long firstZoneId){
        return ResponseVO.getSuccessResponseVo(zoneService.selectTwoZones(userId, firstZoneId));
    }

    @ApiOperation("二级团是否可返利及是否失效")
    @GetMapping("selectTwoZone/{userId}/{firstZoneId}")
    public ResponseVO<ZoneStatusVO> selectTwoZone(@PathVariable String userId, @PathVariable Long firstZoneId){
        return ResponseVO.getSuccessResponseVo(zoneService.selectTwoZone(userId, firstZoneId));
    }

    @ApiOperation("通过邀请码入团")
    @PostMapping("invitationCode")
    public ResponseVO<String> invitationCode(@RequestBody UserByZoneInvitationCodeDTO userByZoneInvitationCodeDTO) {
        return zoneService.joinZone(userByZoneInvitationCodeDTO);
    }

}
