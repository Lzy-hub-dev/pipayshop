package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.PageVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.entity.vo.UserInfoVO;
import com.example.pipayshopapi.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户数据表 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "用户数据接口",tags = "用户数据接口")
@RestController
@RequestMapping("/pipayshopapi/user-info")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;


    @GetMapping("selectUserInfoByUid/{uid}")
    @ApiOperation("根据用户Id查找用户数据表的基本信息")
    public ResponseVO selectUserInfoByUid(@PathVariable String uid){
        try {
            UserInfoVO userInfoVO = userInfoService.selectUserInfoByUid(uid);
            return ResponseVO.getSuccessResponseVo(userInfoVO);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据用户Id查找用户数据表的基本信息失败，请联系后台人员");
        }
    }

    @PostMapping("updateUserInfoByUid")
    @ApiOperation("根据用户Id更改用户数据表的基本信息")
    public ResponseVO updateUserInfoByUid(@RequestBody UserInfoVO userInfoVO){
        try {
            boolean result = userInfoService.updateUserInfoByUid(userInfoVO);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据用户Id更改用户数据表的基本信息成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据用户Id更改用户数据表的基本信息失败，请联系后台人员");
        }
    }

}
