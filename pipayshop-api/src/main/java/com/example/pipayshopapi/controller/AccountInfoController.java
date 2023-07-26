package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.service.AccountInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户账户表 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "用户账户接口",tags = "用户账户接口")
@RestController
@RequestMapping("/pipayshopapi/account-info")
public class AccountInfoController {
    @Resource
    private AccountInfoService accountInfoService;


    @GetMapping ("selectAccountById/{uid}")
    @ApiOperation("根据用户Id查找用户账户表的积分余额和pi币余额")
    public ResponseVO selectAccountById(@PathVariable String uid){
        try {
                AccountInfoVO accountInfoVO = accountInfoService.selectAccountById(uid);
            return ResponseVO.getSuccessResponseVo(accountInfoVO);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据用户Id查找用户账户表的积分余额和pi币余额失败，请联系后台人员");
        }
    }

    @PostMapping ("addAccountById/{uid}")
    @ApiOperation("根据用户Id增加用户账户表的积分余额和pi币余额")
    public ResponseVO addAccountById(@RequestBody FrontAccountInfoVO frontAccountInfoVO ,@PathVariable String uid){
        try {
            boolean result = accountInfoService.addAccountById(frontAccountInfoVO,uid);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据用户Id增加用户账户表的积分余额和pi币余额成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("根据用户Id增加用户账户表的积分余额和pi币余额失败，请联系后台人员");
        }
    }

    @PostMapping ("subAccountById/{uid}")
    @ApiOperation("根据用户Id减少用户账户表的积分余额")
    public ResponseVO subAccountById(@RequestBody FrontAccountInfoVO frontAccountInfoVO ,@PathVariable String uid){
        try {
            boolean result = accountInfoService.subAccountById(frontAccountInfoVO,uid);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据用户Id减少用户账户表的积分余额成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("根据用户Id减少用户账户表的积分余额失败，请联系后台人员");
        }
    }

}
