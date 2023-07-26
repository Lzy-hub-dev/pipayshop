package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ItemCommodityInfoService;
import com.example.pipayshopapi.service.ShopCommodityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 网店的商品表 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "商品接口",tags = "商品接口")
@RestController
@RequestMapping("/pipayshopapi/item-commodity-info")
@Slf4j
public class ItemCommodityInfoController {


    @Resource
    private ItemCommodityInfoService commodityInfoService;


    @GetMapping("commodityOfCateList")
    @ApiOperation("某二级分类下的商品列表分页展示")
    public ResponseVO<PageDataVO> commodityOfCateList(@ModelAttribute commodityPageVO commodityPageVO) {
        try {
            PageDataVO pageDataVO = commodityInfoService.commodityOfCateList(commodityPageVO);
            if (pageDataVO == null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(pageDataVO);
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
            throw new BusinessException("某二级分类下的商品列表分页展示失败，请联系后台人员");
        }
    }




}
