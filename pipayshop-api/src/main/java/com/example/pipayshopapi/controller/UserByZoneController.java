package com.example.pipayshopapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.UserByZone;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.service.UserByZoneService;
import com.example.pipayshopapi.util.InviteCodeGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  用户专区信息controller
 * </p>
 *
 * @author c_shunyi
 * @since 2023-11-21
 */
@Api(value = "用户专区信息",tags = "用户专区信息")
@RestController
@RequestMapping("/pipayshopapi/userByZone")
public class UserByZoneController {

    @Autowired
    UserByZoneService userByZoneService;

    @ApiOperation("添加用户专区信息")
    @PostMapping("addUserByZone")
    public ResponseVO<String> addUserByZone(@RequestBody UserByZone userByZone){
        if (userByZoneService.getOne(new QueryWrapper<UserByZone>()
                        .eq("user_id", userByZone.getUserId())) != null){
            return ResponseVO.getSuccessResponseVo("用户已存在");
        }else {
            userByZoneService.save(userByZone);
            return ResponseVO.getSuccessResponseVo("用户添加成功");
        }
    }

    @ApiOperation("根据用户ID查询用户专区信息")
    @GetMapping("selectUserZoneByUid/{uid}")
    public ResponseVO<UserByZone> SelectUserByZone(@PathVariable String uid){
        return ResponseVO.getSuccessResponseVo(userByZoneService.getOne(new QueryWrapper<UserByZone>()
                        .eq("user_id", uid)));
    }

    @ApiOperation("升级为团长")
    @PutMapping("zoneLeader")
    public ResponseVO<String> zoneLeader(@RequestBody UserByZone userByZone){
        if(userByZoneService.getOne(new QueryWrapper<UserByZone>()
                .eq("user_id", userByZone.getUserId())
                .eq("opening_group_qualification", 0)) != null){
            return ResponseVO.getSuccessResponseVo("升级为团长失败,不具备升级资格");
        }
        if(userByZoneService.getOne(new QueryWrapper<UserByZone>()
                .eq("user_id", userByZone.getUserId())
                .eq("is_group_leader", 1)) != null){
            return ResponseVO.getSuccessResponseVo("升级为团长失败,已经是团长身份");
        }
        userByZone.setIsGroupLeader(1);
        userByZone.setInvitationCode(InviteCodeGenerator.generateInviteCode());
        userByZoneService.update(userByZone, new UpdateWrapper<UserByZone>()
                .eq("user_id", userByZone.getUserId()));
        return ResponseVO.getSuccessResponseVo("升级为团长成功，邀请码为："+userByZone.getInvitationCode());
    }

    @ApiOperation("用户输入邀请码进行关系绑定")
    @PutMapping("invitationCode")
    public ResponseVO<String> invitationCode(@RequestBody UserByZone userByZone) {
        if (userByZoneService.getOne(new QueryWrapper<UserByZone>()
                .eq("user_id", userByZone.getUserId())).getSuperiorUserId() != null) {
            return ResponseVO.getSuccessResponseVo("关系绑定失败，已被其他用户邀请过");
        } else {
            UserByZone superiorUser = userByZoneService.getOne(new QueryWrapper<UserByZone>()
                    .eq("invitation_code", userByZone.getInvitationCode()));
            if (superiorUser != null) {
                if (superiorUser.getUserId().equals(userByZone.getUserId())) {
                    return ResponseVO.getSuccessResponseVo("关系绑定失败，无法绑定自己");
                }
                userByZoneService.update(null, new UpdateWrapper<UserByZone>()
                        .eq("user_id", userByZone.getUserId())
                        .set("superior_user_id", superiorUser.getUserId()));
                return ResponseVO.getSuccessResponseVo("关系绑定成功");
            } else {
                return ResponseVO.getSuccessResponseVo("关系绑定失败，该邀请码用户不存在或已注销");
            }
        }
    }
}
