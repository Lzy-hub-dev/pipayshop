package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.ItemOrderInfoVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.entity.vo.ShopOrderInfoVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopOrderInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 订单数据表 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@Controller
@RequestMapping("/pipayshopapi/shop-order-info")
public class ShopOrderInfoController {

    @Resource
    private ShopOrderInfoService shopOrderInfoService;
    @GetMapping("selectUserShopOrders/{userId}")
    @ApiOperation("获取实体店用户订单列表")
    public ResponseVO selectUserShopOrders(@PathVariable String userId) {
        try {
            List<ShopOrderInfoVO> shopOrderInfoVOS = shopOrderInfoService.selectUserShopOrders(userId);
            if (shopOrderInfoVOS == null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(shopOrderInfoVOS);
        } catch (Exception e) {

            throw new BusinessException("获取实体店用户订单列表失败，请联系后台人员");
        }
    }
}
