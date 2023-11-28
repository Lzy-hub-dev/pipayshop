package com.example.pipayshopapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.UserByZone;
import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.mapper.UserInfoMapper;
import com.example.pipayshopapi.mapper.ZoneLeaderConfigurationMapper;
import com.example.pipayshopapi.service.FirstZoneUserService;
import com.example.pipayshopapi.service.UserByZoneService;
import com.example.pipayshopapi.service.ZoneLeaderConfigurationService;
import com.example.pipayshopapi.service.ZoneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;


/**
 * <p>
 *  用户专区信息controller
 * </p>
 *
 * @author c_shunyi
 * @since 2023-11-21
 */
@Api(tags = "用户专区信息")
@RestController
@RequestMapping("/pipayshopapi/UserByZone")
public class UserByZoneController {

    @Autowired
    UserByZoneService userByZoneService;
    @Autowired
    ZoneService ZoneService;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    ZoneLeaderConfigurationService zoneLeaderConfigurationService;
    @Autowired
    ZoneLeaderConfigurationMapper zoneLeaderConfigurationMapper;
    @Autowired
    FirstZoneUserService firstZoneUserService;

    @ApiOperation("添加用户专区信息")
    @PostMapping("addUserByZone")
    public ResponseVO<String> addUserByZone(@RequestBody Map<String, Object> requestBody){
        String userId = requestBody.get("userId").toString();
        if(userId.isEmpty())
            return ResponseVO.getFalseResponseMsg("请求失败，用户未登录，创建用户专区信息失败");
        if (userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                .eq("uid", userId)) == null)
            return ResponseVO.getFalseResponseMsg("请求失败，无该用户信息");
        if (userByZoneService.getOne(new QueryWrapper<UserByZone>()
                        .eq("user_id", userId)) != null){
            return ResponseVO.getFalseResponseMsg("请求失败，用户专区信息已存在，创建用户专区信息失败");
        } else {
            UserByZone userByZone = new UserByZone();
            userByZone.setUserId(userId);
            userByZone.setUserThreshold(zoneLeaderConfigurationService.getById(1).getThresholdSum());
            userByZoneService.save(userByZone);
            return ResponseVO.getSuccessResponseMsg(null, "用户专区信息添加成功");
        }
    }

    @ApiOperation("根据用户ID查询用户专区信息")
    @GetMapping("selectUserZoneByUid/{userId}")
    public ResponseVO<UserByZone> SelectUserByZone(@PathVariable String userId){
        return ResponseVO.getSuccessResponseVo(userByZoneService.getOne(new QueryWrapper<UserByZone>()
                        .eq("user_id", userId)));
    }

    @ApiOperation("升级为团长")
    @PostMapping("zoneLeader")
    public ResponseVO<String> zoneLeader(@RequestBody Map<String, Object> requestBody){
        String userId = requestBody.get("userId").toString();
        BigDecimal thresholdSum = zoneLeaderConfigurationService.getById(1).getThresholdSum();
        if(userByZoneService.getOne(new QueryWrapper<UserByZone>()
                .eq("user_id", userId)).getZoneConsumptionSum().compareTo(thresholdSum) < 0){
            return ResponseVO.getFalseResponseMsg("升级为团长失败,消费金额未达到门槛，不具备升级资格");
        }
        if(userByZoneService.getOne(new QueryWrapper<UserByZone>()
                .eq("user_id", userId)
                .eq("is_group_leader", 1)) != null){
            return ResponseVO.getFalseResponseMsg("升级为团长失败,已经是团长身份");
        }
        userByZoneService.update(null, new UpdateWrapper<UserByZone>()
                .eq("user_id", userId)
                .set("is_group_leader", 1)
                .set("opening_group_qualification", 1));
        return ResponseVO.getSuccessResponseMsg(null, "升级为团长成功");
    }

}
