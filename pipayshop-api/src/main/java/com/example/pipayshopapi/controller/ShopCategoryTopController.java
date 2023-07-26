package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.entity.vo.ShopCategoryVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopCategoryTopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 实体店一级分类表
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "实体店接口",tags = "实体店接口")
@RestController
@RequestMapping("/pipayshopapi/shop-category-top")
public class ShopCategoryTopController {

    @Resource
    private ShopCategoryTopService shopCategoryTopService;

    @GetMapping ("selectAllContentList")
    @ApiOperation("查找分类列表")
    public ResponseVO<List> selectAllContentList(){
        try {
            List<ShopCategoryVO> list = shopCategoryTopService.selectAllContentList();
            if (list==null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(list);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("查找分类列表失败，请联系后台人员");
        }
    }

}
