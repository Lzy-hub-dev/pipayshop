package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.enums.Language;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.entity.vo.UserInfoVO;
import com.example.pipayshopapi.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @PostMapping("updateLanguageByUid/{uid}/{language}")
    @ApiOperation("根据用户Id更改用户语言标识")
    public ResponseVO updateLanguageByUid(@PathVariable String uid,@PathVariable Integer language){
        try {
            boolean result = userInfoService.updateLanguageByUid(uid, language);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据用户Id更改用户语言标识成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据用户Id更改用户语言标识失败，请联系后台人员");
        }
    }

    @PostMapping("updateCountryByUid/{uid}/{country}")
    @ApiOperation("根据用户Id更改用户国家标识")
    public ResponseVO updateCountryByUid(@PathVariable String uid,@PathVariable Integer country){
        try {
            boolean result = userInfoService.updateCountryByUid(uid, country);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据用户Id更改用户国家标识成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据用户Id更改用户国家标识失败，请联系后台人员");
        }
    }

}
