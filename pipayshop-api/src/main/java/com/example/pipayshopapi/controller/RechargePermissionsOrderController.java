package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.RechargePermissionsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
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
 * @since 2023-08-06
 */
@Slf4j
@Api(value = "记录用户充值店铺和商品的数量订单",tags = "记录用户充值店铺和商品的数量订单")
@RestController
@RequestMapping("/pipayshopapi/recharge-permissions-order")
public class RechargePermissionsOrderController {

    @Resource
    RechargePermissionsOrderService rechargePermissionsOrderService;
    /**
     * 下单提高一个用户可绑定的实体店余额数
     */
    @PostMapping("addShopSum/{orderId}")
    @ApiOperation("下单提高一个用户可绑定的实体店余额数")
    public ResponseVO<String> addShopSum(@PathVariable String orderId) {
        try {
            boolean flag = rechargePermissionsOrderService.addShopSum(orderId);
            if (!flag){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("下单提高一个用户可绑定的实体店余额数成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }


    @PostMapping("updateUploadBalanceInfo/{orderId}")
    @ApiOperation("更新网店可添加商品余额信息")
    public ResponseVO<String> updateUploadBalanceInfo(@PathVariable String orderId){
        try {
            boolean result = rechargePermissionsOrderService.updateUploadBalanceInfo(orderId);
            if(!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("更新成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }


    @PostMapping("getUploadBalanceNoPayOrder")
    @ApiOperation("生成充值余额数据的未支付订单，并返回订单id")
    public ResponseVO<String> getUploadBalanceNoPayOrder(String token){
        try {
            String orderId = rechargePermissionsOrderService.getUploadBalanceNoPayOrder(token);
            if(orderId == null || "".equals(orderId)){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(orderId);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("生成充值余额数据的未支付订单失败，请联系后台人员");
        }
    }

}
