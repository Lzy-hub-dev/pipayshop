package com.example.pipayshopapi.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 实体店的标签 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "接口",tags = "接口")
@RestController
@RequestMapping("/pipayshopapi/shop-tags")
public class ShopTagsController {

}
