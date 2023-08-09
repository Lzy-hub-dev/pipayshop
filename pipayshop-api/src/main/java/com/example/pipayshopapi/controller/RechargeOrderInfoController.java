package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.dto.CompleteDTO;
import com.example.pipayshopapi.entity.dto.IncompleteDTO;
import com.example.pipayshopapi.entity.dto.PaymentDTO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.RechargeOrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 商户订单数据表 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-08-08
 */
@RestController
@RequestMapping("/pipayshopapi/recharge-order-info")
@Api(value = "充值订单处理接口",tags = "充值订单处理接口")
@Slf4j
public class RechargeOrderInfoController {

    @Resource
    RechargeOrderInfoService rechargeOrderInfoService;
    /**
     * 处理未完成的订单
     */
    @PostMapping("payOrder/incomplete")
    @ApiOperation("处理充值未完成的订单")
    public ResponseVO incomplete(@RequestBody IncompleteDTO incompleteDTO) {

        try {

            return rechargeOrderInfoService.incomplete(incompleteDTO);
        } catch (Exception e) {
            log.error("报错如下：{}", e.getMessage());
            throw new BusinessException("支付失败，请联系后台人员"+e.getLocalizedMessage()+e.toString()+e.getCause().toString());
        }

    }

    /**
     * 前端请求支付授权,在本地订单创建后调
     */
    @PostMapping("payOrder/approve")
    @ApiOperation("前端请求支付授权,在本地订单创建后调")
    public ResponseVO approve(@RequestBody PaymentDTO paymentDTO) {
        try {
            return rechargeOrderInfoService.approve(paymentDTO);
        } catch (Exception e) {
            log.error("报错如下：{}", e.getMessage());
            throw new BusinessException("支付失败，请联系后台人员"+e.getLocalizedMessage()+e.toString()+e.getCause().toString());
        }
    }

    /**
     * 前端支付完成,余额支付直接调用此方法
     */
    @PostMapping("payOrder/complete")
    @ApiOperation("前端支付完成,余额支付直接调用此方法")
    public ResponseVO complete(@RequestBody CompleteDTO completeDTO) {

        try {

            return rechargeOrderInfoService.complete(completeDTO);
        } catch (Exception e) {
            log.error("报错如下：{}", e.getMessage());
            throw new BusinessException("支付失败，请联系后台人员"+e.getLocalizedMessage()+e.toString()+e.getCause().toString());
        }

    }


    /**
     * 取消支付,订单关闭
     */
    @PostMapping("payOrder/cancelled/{rechargeOrderId]")
    @ApiOperation("取消支付,订单关闭")
    public ResponseVO<String> cancelled(@PathVariable String rechargeOrderId) {

        try {
            Boolean order = rechargeOrderInfoService.cancelled(rechargeOrderId);
            if (!order){throw  new BusinessException("取消订单失败");}
            return ResponseVO.getSuccessResponseVo("取消订单成功");
        } catch (Exception e) {
            log.error("报错如下：{}", e.getMessage());
            throw new BusinessException("取消失败，请联系后台人员"+e.getLocalizedMessage()+e.toString()+e.getCause().toString());
        }

    }

    /**
     * 生成pi币充值的未支付订单
     */
    @PostMapping("getNoPidOrder")
    @ApiOperation("生成pi币充值的未支付订单，并返回订单的id")
    public ResponseVO<String> getNoPidOrder(String token) {
        try {
            String orderId = rechargeOrderInfoService.getNoPidOrder(token);
            if (orderId== null || "".equals(orderId)){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(orderId);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new BusinessException("生成pi币充值的未支付订单失败，请联系后台人员");
        }
    }
}
