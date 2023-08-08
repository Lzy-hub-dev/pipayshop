package com.example.pipayshopapi.controller;


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

    @GetMapping("getAll")
    @ApiOperation("查询相关配置信息")
    public ResponseVO<List<Configuration>> getAll() {
        List<Configuration> all = configurationService.getAll();
        return ResponseVO.getSuccessResponseVo(all);

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
