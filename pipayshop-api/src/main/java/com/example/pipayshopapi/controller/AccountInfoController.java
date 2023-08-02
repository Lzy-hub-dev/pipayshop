package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.AccountInfoVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.service.AccountInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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


    @PostMapping("selectAccountById/{uid}")
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

}
