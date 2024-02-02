package com.example.pipayshopapi.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.TradinPost;
import com.example.pipayshopapi.entity.dto.PayPalDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.AccountInfoService;
import com.example.pipayshopapi.service.TradinOrderService;
import com.example.pipayshopapi.service.TradinPostService;
import com.example.pipayshopapi.service.TradinRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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

    @Resource
    private TradinOrderService tradinOrderService;

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
           boolean insert= tradinPostService.publishTradition(token);
            if ( ! insert ){
                throw new RuntimeException();
            }
            return ResponseVO.getSuccessResponseVo("发布成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("发布失败");
        }
    }


    @GetMapping("selectTraditionList/{typeId}/{page}/{limit}")
    @ApiOperation("根据piName获取交易列表")
    public ResponseVO selectTraditionList(@PathVariable Integer typeId,
                                          @PathVariable Integer page,
                                          @PathVariable Integer limit,
                                          @RequestParam(required = false, defaultValue = "") String piName){

            PageDataVO traditionDTOList= tradinPostService.selectTraditionListByPiName(typeId,page,limit, piName);
            return ResponseVO.getSuccessResponseVo(traditionDTOList);
    }

    @PutMapping("cancelTradition/{piName}/{tradinId}")
    @ApiOperation("取消交易")
    public ResponseVO<String> cancelTradition(@PathVariable String tradinId, @PathVariable String piName ){

        tradinPostService.cancelTradition(tradinId, piName);
        return ResponseVO.getSuccessResponseVo("取消交易成功");
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

    @PostMapping("generateTradeOrder")
    @ApiOperation("生成交易订单")
    public ResponseVO<String> generateTradeOrder(String tradinId,String buyerId){
        try {
            String orderId=tradinOrderService.generateTradeOrder(tradinId,buyerId);
            return ResponseVO.getSuccessResponseVo(orderId);
        }catch (BusinessException e){
            return ResponseVO.getSuccessResponseVo(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.getSuccessResponseVo("生成交易订单失败");
        }
    }


    @PostMapping("upLoadPointBalance")
    @ApiOperation("提交积分")
    public ResponseVO upLoadPointBalance( String token){
        try {
            boolean insert = tradinOrderService.upLoadPointBalance(token);
            if ( !insert ){
                throw new RuntimeException("提交转积分失败");
            }

            return ResponseVO.getSuccessResponseVo("提交转积分成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("提交转积分失败");
        }
    }
    @PostMapping("upLoadImg")
    @ApiOperation("提交pi币转账凭证")
    public ResponseVO upLoadImg(MultipartFile file, String token){
        try {
            boolean insert = tradinOrderService.upLoadImg(file,token);
            if ( !insert ){
                throw new RuntimeException();
            }
            return ResponseVO.getSuccessResponseVo("提交凭证成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("提交凭证失败");
        }
    }

    @PostMapping("confirmTransaction")
    @ApiOperation("确认交易")
    public ResponseVO confirmTransaction(String token){
        try {
            boolean update =tradinOrderService.confirmTransaction(token);
            if (!update){
                throw new RuntimeException("确认交易失败");
            }
            return ResponseVO.getSuccessResponseVo("确认交易成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("确认交易失败");
        }
    }


    @GetMapping("selectTradinPostByUid")
    @ApiOperation("获取用户交易列表")
    public ResponseVO selectTradinPostByUid(String token){
        try {
            List<TraditionListVO> traditionDTOList= tradinPostService.selectTradinPostByUid(token);
            return ResponseVO.getSuccessResponseVo(traditionDTOList);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("获取交易列表失败");
        }
    }



    @GetMapping("selectTradinyOrderByUid")
    @ApiOperation("获取用户交易订单列表")
    public ResponseVO selectTradinyOrderByUid(String token){
        try {
            List<TradinOrderListVO> tradinyOrderListVOS= tradinOrderService.selectTradinyOrderByUid(token);
            return ResponseVO.getSuccessResponseVo(tradinyOrderListVOS);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("获取用户交易订单列表失败");
        }
    }


    @GetMapping("selectTradinOrderDetail/{orderId}")
    @ApiOperation("交易双方获取交易订单详情接口")
    public ResponseVO selectTradinyOrderDetail(@PathVariable String orderId){
        try {
            TradinOrderDetailVO tradinOrderDetailVO =tradinOrderService.selectTradinOrderDetail(orderId);
            return ResponseVO.getSuccessResponseVo(tradinOrderDetailVO);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("交易双方获取交易详情失败");
        }
    }


    @GetMapping("selectAllTradinRate")
    @ApiOperation("获取汇率")
    public ResponseVO selectAllTradinRate(  ){
        try {

            List<TradinRateVO> tradinRateDTOList=tradinRateService.selectAllTradinRate();
            return ResponseVO.getSuccessResponseVo(tradinRateDTOList);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("获取汇率失败");
        }
    }


    @PostMapping("/api/orders/{orderId}/capture")
    @ApiOperation("获取Payment")
    public Object capturePayment(@PathVariable("orderId") String orderId) {
       return accountInfoService.capturePayment(orderId);

    }

    @PostMapping("/api/orders")
    @ApiOperation("生成订单")
    public Object createOrder(@RequestBody PayPalDTO payPalDTO) {
        return accountInfoService.createOrder(payPalDTO);
    }
}
