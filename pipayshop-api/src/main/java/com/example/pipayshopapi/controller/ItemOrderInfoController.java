package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.ItemInfoVO;
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

import org.springframework.stereotype.Controller;
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

    @GetMapping("selectItemOrders/{userId}")
    @ApiOperation("获取网店用户订单列表")
    public ResponseVO selectItemOrders(@PathVariable String userId) {
        try {
            List<ItemOrderInfoVO> itemOrderInfoVOS = itemOrderInfoService.selectItemOrders(userId);
            if (itemOrderInfoVOS == null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(itemOrderInfoVOS);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("获取网店用户订单列表失败，请联系后台人员");
        }
    }
}
