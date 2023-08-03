package com.example.pipayshopapi.controller;

import com.example.pipayshopapi.service.ShopCategoryService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 实体店二级分类表 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "实体店二级分类",tags = "实体店二级分类")
@RestController
@RequestMapping("/pipayshopapi/shop-category")
public class ShopCategoryController {

    @Resource
    ShopCategoryService shopCategoryService;

// toDO

}
