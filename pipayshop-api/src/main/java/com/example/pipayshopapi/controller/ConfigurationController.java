package com.example.pipayshopapi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pipayshopapi.entity.Configuration;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ConfigurationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-08-08
 */
@RestController
@Api(value = "配置接口",tags = "配置接口")
@RequestMapping("/pipayshopapi/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @GetMapping("getEnabledPi")
    @ApiOperation("是否开启PI汇率自动转换")
    public boolean getEnabledPi() {
        Configuration configuration = configurationService
                .getBaseMapper()
                .selectOne(new QueryWrapper<Configuration>()
                .eq("del_flag", 0)
                .eq("content", "pi")
        );
        return configuration != null;
    }

    @GetMapping("getEshopLimitPrice")
    @ApiOperation("获取提升网店商品额度价格")
    public ResponseVO<String> getEshopLimitPrice() {
        Configuration configuration = configurationService
                .getBaseMapper()
                .selectOne(new QueryWrapper<Configuration>()
                        .eq("del_flag", 0)
                        .eq("content", "eshop_limit_price")
                );
        return ResponseVO.getSuccessResponseVo(configuration.getConfigValue());

    }

    @GetMapping("getshopLimitPrice")
    @ApiOperation("获取提升实体店额度价格")
    public ResponseVO<String> getShopLimitPrice() {
        Configuration configuration = configurationService
                .getBaseMapper()
                .selectOne(new QueryWrapper<Configuration>()
                        .eq("del_flag", 0)
                        .eq("content", "shop_limit_price")
                );
        return ResponseVO.getSuccessResponseVo(configuration.getConfigValue());

    }
    @PostMapping("setValueByKey")
    @ApiOperation("根据key修改value")
    public ResponseVO<String> setValueByKey(String key,String value) {
        boolean flag = configurationService.setValueByKey(key, value);
        if (!flag) {
            throw new RuntimeException();
        }
        return ResponseVO.getSuccessResponseVo("修改成功!");

    }
}
