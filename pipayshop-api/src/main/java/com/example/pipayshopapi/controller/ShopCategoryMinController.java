package com.example.pipayshopapi.controller;

import com.example.pipayshopapi.service.ShopCategoryMinService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 实体店三级分类表 前端控制器
 * @author wzx
 */
@Api(value = "实体店三级分类",tags = "实体店三级分类")
@RestController
@RequestMapping("/pipayshopapi/shop-category-min")
public class ShopCategoryMinController {

    @Resource
    ShopCategoryMinService shopCategoryMinService;
// todo


}