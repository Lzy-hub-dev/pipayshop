package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.OrderInfo;
import com.example.pipayshopapi.entity.vo.OrderPageVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.OrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 订单数据表 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "订单管理接口",tags = "订单管理接口")
@RestController
@RequestMapping("/pipayshopapi/order-info")
@Slf4j
public class OrderInfoController {

    @Resource
    private OrderInfoService orderInfoService;


    @GetMapping("orderList")
    @ApiOperation("用户的全部订单列表分页展示")
    public ResponseVO<PageDataVO> orderList(@ModelAttribute OrderPageVO orderPageVO) {
        try {
            PageDataVO pageDataVO = orderInfoService.OrderList(orderPageVO);
            if (pageDataVO == null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(pageDataVO);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("用户的全部订单列表分页展示失败，请联系后台人员");
        }
    }


    @GetMapping("orderListByStatus")
    @ApiOperation("用户的不同状态的订单列表分页展示")
    public ResponseVO<PageDataVO> orderListByStatus(@ModelAttribute  OrderPageVO orderPageVO) {
        try {
            PageDataVO pageDataVO = orderInfoService.OrderListByStatus(orderPageVO);
            if (pageDataVO == null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(pageDataVO);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("用户的不同状态的订单列表分页展示失败，请联系后台人员");
        }
    }


    @PostMapping("addOrder")
    @ApiOperation("生成订单")
    public ResponseVO addOrder(@RequestBody OrderInfo orderInfo){

        try{
            boolean result= orderInfoService.addOrder(orderInfo);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("生成订单成功");
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("生成订单失败，请联系后台人员");
        }
    }


//    @PostMapping("payOrder/{orderId}")
//    @ApiOperation("支付订单")
//    public ResponseVO payOrder(@PathVariable String orderId){
//
//        try{
//            boolean result= orderInfoService.payOrder(orderId);
//            if (!result) {
//                throw new Exception();
//            }
//            return ResponseVO.getSuccessResponseVo("支付订单成功");
//        }catch (Exception e){
//            log.error(e.getMessage());
//            throw new BusinessException("支付订单失败，请联系后台人员");
//        }
//    }
//
//
//    @PostMapping("finishOrder/{orderId}")//
//    @ApiOperation("完成订单")
//    public ResponseVO finishOrder(@PathVariable String orderId){
//
//        try{
//            boolean result= orderInfoService.finishOrder(orderId);
//            if (!result) {
//                throw new Exception();
//            }
//            return ResponseVO.getSuccessResponseVo("完成订单成功");
//        }catch (Exception e){
//            log.error(e.getMessage());
//            throw new BusinessException("完成订单失败，请联系后台人员");
//        }
//    }


    @PostMapping("logicDeleteOrder/{orderId}")
    @ApiOperation("逻辑删除订单")
    public ResponseVO logicDeleteOrder(@PathVariable String orderId){

        try{
            boolean result= orderInfoService.logicDeleteOrder(orderId);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("逻辑删除订单成功");
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("逻辑删除订单失败，请联系后台人员");
        }
    }

    @PostMapping("realDeleteOrder/{orderId}")
    @ApiOperation("真实删除订单")
    public ResponseVO realDeleteOrder(@PathVariable String orderId){

        try{
            boolean result= orderInfoService.realDeleteOrder(orderId);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("真实删除订单成功");
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("真实删除订单失败，请联系后台人员");
        }
    }


}
