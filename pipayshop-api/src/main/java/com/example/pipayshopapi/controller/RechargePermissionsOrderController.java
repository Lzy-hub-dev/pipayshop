package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.RechargePermissionsOrder;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.RechargePermissionsOrderVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.AccountInfoService;
import com.example.pipayshopapi.service.ItemInfoService;
import com.example.pipayshopapi.service.RechargePermissionsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Api(value = "记录用户充值店铺和商品的数量订单",tags = "记录用户充值店铺和商品的数量订单")
@RestController
@RequestMapping("/pipayshopapi/recharge-permissions-order")
public class RechargePermissionsOrderController {

    @Resource
    private RechargePermissionsOrderService rechargePermissionsOrderService;

    @PostMapping("updateUploadBalanceInfo")
    @ApiOperation("更新网店可添加商品余额信息")
    public ResponseVO updateUploadBalanceInfo(@RequestBody RechargePermissionsOrderVO rechargePermissionsOrderVO){
        try {
            Boolean result = rechargePermissionsOrderService.updateUploadBalanceInfo(rechargePermissionsOrderVO);
            if(result == false){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("更新成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("更新网店可添加商品余额信息失败，请联系后台人员");
        }
    }

}
