package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.dto.RebateDTO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.service.RebateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.Map;

/**
 * <p>
 *  用户专区返佣controller
 * </p>
 *
 * @author c_shunyi
 * @since 2023-11-23
 */
@Api(tags = "用户专区返佣接口")
@RestController
@RequestMapping("/pipayshopapi/Rebate")
public class RebateController {

    @Autowired
    RebateService rebateService;

    @ApiOperation("一级团返佣")
    @PostMapping("firstRebate")
    public ResponseVO<String> firstRebate(@RequestBody RebateDTO rebateDTO){
        return rebateService.firstRebate(rebateDTO);
    }
    @ApiOperation("二级团返佣")
    @PostMapping("twoRebate")
    public ResponseVO<String> twoRebate(@RequestBody RebateDTO rebateDTO){
        return rebateService.twoRebate(rebateDTO);
    }
}
