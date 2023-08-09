package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.BUserLoginVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.BUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-08-09
 */
@RestController
@Api(value = "B端接口",tags = "B端接口")
@RequestMapping("/pipayshopapi/b-user-info")
public class BUserInfoController {

    @Resource
    BUserInfoService bUserInfoService;

    /**
     * 校验登录
     */
    @GetMapping("login")
    @ApiOperation("校验登录")
    public ResponseVO<String> login(BUserLoginVO bUserLoginVO){
        try {
            String token = bUserInfoService.login(bUserLoginVO);
            if (token==null || "".equals(token)){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(token);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("登录失败，请检验密码和账号是否匹配");
        }
    }

    /**
     * 修改密码
     */
    @PostMapping("updatePassWord")
    @ApiOperation("修改密码")
    public ResponseVO<String> updatePassWord(BUserLoginVO bUserLoginVO){
        try {
            boolean flag = bUserInfoService.updatePassWord(bUserLoginVO);
            if (!flag){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("修改密码成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("修改密码失败，请检验密码和账号是否匹配");
        }
    }

}
