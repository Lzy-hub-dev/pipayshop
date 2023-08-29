package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.BUserLoginVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.BUserInfoService;
import com.example.pipayshopapi.util.TokenUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

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

    /**
     * 修改密码
     */
    @GetMapping("selectAccountBalance/{uid}")
    @ApiOperation("b端获取商户账户积分")
    public ResponseVO<BigDecimal> selectAccountBalance(@PathVariable String uid){
        try {
            BigDecimal bigDecimal = bUserInfoService.selectAccountBalance(uid);

            return ResponseVO.getSuccessResponseVo(bigDecimal);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("b端获取商户账户积分失败");
        }
    }


    /**
     * b端商户提现
     */
    @PostMapping("userWithDraw/{token}")
    @ApiOperation("b端商户提现")
    public ResponseVO<String> userWithDraw(@PathVariable String token) {
        try {
            Claims dataFromToken = TokenUtil.getDataFromToken(token);
            String uid = (String) dataFromToken.get("uid");
            String balance = (String) dataFromToken.get("balance");
            boolean userWithDraw = bUserInfoService.userWithDraw(uid, new BigDecimal(balance));
            if ( !userWithDraw){
                return ResponseVO.getFalseResponseVo("提现失败");
            }
            return ResponseVO.getSuccessResponseVo("提现成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("b端获取商户提现失败");
        }
    }

    @GetMapping("selectUserWithdrawalRecord")
    @ApiOperation("b端获取提现记录列表")
    public ResponseVO<PageDataVO> selectUserWithdrawalRecord(String uid,Integer page,Integer limit) {
        try {
            PageDataVO pageDataVO = bUserInfoService.selectUserWithdrawalRecord(uid, page, limit);
            if (pageDataVO == null){throw new Exception();}
            return ResponseVO.getSuccessResponseVo(pageDataVO);
        } catch (Exception e) {
            throw new BusinessException("b端获取提现记录列表失败，请联系后台人员");
        }
    }
}
