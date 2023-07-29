package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.ItemOrderInfoVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ItemOrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商户订单数据表 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-07-28
 */

@RequestMapping("/pipayshopapi/item-order-info")
@Api(value = "网店用户订单接口",tags = "网店用户订单接口")
@RestController
@Slf4j
public class ItemOrderInfoController {


    @Resource
    private ItemOrderInfoService itemOrderInfoService;

    @GetMapping("selectUserItemOrders/{userId}")
    @ApiOperation("获取网店用户订单列表")
    public ResponseVO selectUserItemOrders(@PathVariable String userId) {
        try {
            List<ItemOrderInfoVO> itemOrderInfoVOS = itemOrderInfoService.selectUserItemOrders(userId);
            if (itemOrderInfoVOS == null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(itemOrderInfoVOS);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("获取网店用户订单列表失败，请联系后台人员");
        }
    }

    @GetMapping("deleteUserItemOrder/{orderId}")
    @ApiOperation("删除网店用户订单")
    public ResponseVO deleteUserItemOrder(@PathVariable String orderId) {
        try {
            Boolean update = itemOrderInfoService.deleteUserItemOrder(orderId);
            if (!update){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("删除网店用户订单");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("删除网店用户订单失败，请联系后台人员");
        }
    }
    //
    @GetMapping("selectOrderByUerId/{userId}")
    @ApiOperation("获取用户订单列表(包含商品信息)-网店")
    public ResponseVO selectOrderByUerId(@PathVariable("userId") String userId) {
        try {
            List<ItemOrderInfoVO> itemOrderInfoVOS = itemOrderInfoService.selectOrderByUerId(userId);
            return ResponseVO.getSuccessResponseVo(itemOrderInfoVOS);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("获取网店用户订单列表失败，请联系后台人员");
        }
    }


}
