package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.service.ShopHotelRecordService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 记录酒店入住信息表 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-08-02
 */
@Api(value = "记录酒店入住信息接口",tags = "记录酒店入住信息接口")
@RestController
@RequestMapping("/pipayshopapi/shop-hotel-record")
public class ShopHotelRecordController {

}
