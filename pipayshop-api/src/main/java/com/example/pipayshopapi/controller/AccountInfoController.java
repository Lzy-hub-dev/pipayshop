package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.dto.TradinPostDTO;
import com.example.pipayshopapi.entity.dto.TransactionDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.service.AccountInfoService;
import com.example.pipayshopapi.service.TradinPostService;
import com.example.pipayshopapi.service.TradinRateService;
import com.example.pipayshopapi.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @Resource
    private TradinRateService tradinRateService;

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
    @GetMapping("selectTraditionList/{typeId}/{page}/{limit}")
    @ApiOperation("获取交易列表")
    public ResponseVO selectTraditionList(@PathVariable Integer typeId,@PathVariable Integer page,@PathVariable Integer limit ){
        try {

            PageDataVO traditionDTOList= tradinPostService.selectTraditionList(typeId,page,limit);
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
    @ApiOperation("提交pi币转账凭证")
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
    @PostMapping("upLoadPointBalance")
    @ApiOperation("提交转积分")
    public ResponseVO upLoadPointBalance( TradinPostDTO tradinPostDTO){
        try {
            boolean insert = tradinPostService.upLoadPointBalance(tradinPostDTO);
            if ( !insert ){
                throw new RuntimeException("提交转积分失败");
            }

            return ResponseVO.getSuccessResponseVo("提交转积分成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("提交转积分失败");
        }
    }
    @GetMapping("selectTradinPostByUid/{userId}")
    @ApiOperation("获取用户交易列表")
    public ResponseVO selectTradinPostByUid(@PathVariable String userId){
        try {
            List<TraditionListVO> traditionDTOList= tradinPostService.selectTradinPostByUid(userId);
            return ResponseVO.getSuccessResponseVo(traditionDTOList);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("获取交易列表失败");
        }
    }


    @PostMapping("confirmTransaction")
    @ApiOperation("确认交易")
    public ResponseVO confirmTransaction( String userId,String tradinId){
        try {
            boolean update =tradinPostService.confirmTransaction(userId,tradinId);
            if (!update){
                throw new RuntimeException("确认交易失败");
            }
            return ResponseVO.getSuccessResponseVo("确认交易成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("确认交易失败");
        }
    }

    @GetMapping("selectAllTradinRate")
    @ApiOperation("获取汇率")
    public ResponseVO selectAllTradinRate(  ){
        try {

            List<TradinRateDTO> tradinRateDTOList=tradinRateService.selectAllTradinRate();
            return ResponseVO.getSuccessResponseVo(tradinRateDTOList);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("提交转积分失败");
        }
    }







    @PostMapping("/api/orders/{orderId}/capture")
    @ApiOperation("获取Payment")
    public Object capturePayment(@PathVariable("orderId") String orderId) {
       return accountInfoService.capturePayment(orderId);

    }

    @PostMapping("/api/orders")
    @ApiOperation("生成订单")
    public Object createOrder() {
        return accountInfoService.createOrder();


    }
}
