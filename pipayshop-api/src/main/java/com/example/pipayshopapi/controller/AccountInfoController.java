package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.dto.TradinPostDTO;
import com.example.pipayshopapi.entity.dto.TransactionDTO;
import com.example.pipayshopapi.entity.vo.AccountInfoVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.entity.vo.TraditionDetailVO;
import com.example.pipayshopapi.entity.vo.TraditionListVO;
import com.example.pipayshopapi.service.AccountInfoService;
import com.example.pipayshopapi.service.TradinPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Resource
    private TradinPostService tradinPostService;

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

    @PostMapping("publishTradition")
    @ApiOperation("发布交易")
    public ResponseVO publishTradition(String token){
        try {
           int insert= tradinPostService.publishTradition(token);
            if (insert < 1){
                throw new RuntimeException();
            }
            return ResponseVO.getSuccessResponseVo("发布成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("发布失败");
        }
    }
    @GetMapping("selectTraditionList/{typeId}")
    @ApiOperation("获取交易")
    public ResponseVO selectTraditionList(@PathVariable Integer typeId ){
        try {
            List<TraditionListVO> traditionDTOList= tradinPostService.selectTraditionList(typeId);
            return ResponseVO.getSuccessResponseVo(traditionDTOList);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("发布失败");
        }
    }

    @GetMapping("selectTraditionDetail/{tradinId}")
    @ApiOperation("获取交易详情")
    public ResponseVO selectTraditionDetail(@PathVariable String tradinId ){
        try {
            TraditionDetailVO traditionDetailVO=tradinPostService.selectTraditionDetail(tradinId);
            return ResponseVO.getSuccessResponseVo(traditionDetailVO);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("获取交易详情失败");
        }
    }
    @PostMapping("upLoadImg")
    @ApiOperation("提交凭证")
    public ResponseVO upLoadImg(MultipartFile file, TradinPostDTO tradinPostDTO){
        try {
            boolean insert = tradinPostService.upLoadImg(file,tradinPostDTO);
            if ( !insert ){
                throw new RuntimeException();
            }
            return ResponseVO.getSuccessResponseVo("提交凭证成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("提交凭证失败");
        }
    }

}
