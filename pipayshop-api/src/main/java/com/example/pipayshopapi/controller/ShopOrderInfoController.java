package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.ItemOrderInfoVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.Impl.ShopOrderInfoServiceImpl;
import com.example.pipayshopapi.service.ShopOrderInfoService;
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
 * 实体店订单数据表 前端控制器
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
@Api(value = "实体店用户订单接口",tags = "实体店用户订单接口")
@RestController
@RequestMapping("/pipayshopapi/shop-order-info")
@Slf4j
public class ShopOrderInfoController {

    @Resource
    private ShopOrderInfoService shopOrderInfoService;
    @GetMapping("selectOrderByUerId/{userId}")
    @ApiOperation("获取用户订单列表(包含商品信息)-实体店")
    public ResponseVO selectOrderByUerId(@PathVariable("userId") String userId) {
        try {
            List<ItemOrderInfoVO> itemOrderInfoVOS = shopOrderInfoService.selectOrderByUerId(userId);
            return ResponseVO.getSuccessResponseVo(itemOrderInfoVOS);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("获取网店用户订单列表失败，请联系后台人员");
        }
    }
}
