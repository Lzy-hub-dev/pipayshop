package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ItemCommodityCategory;
import com.example.pipayshopapi.entity.ItemCommodityInfoCategoryTop;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ItemCommodityCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 网店商品的二级分类表 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "网店二级分类接口",tags = "网店二级分类接口")
@RestController
@RequestMapping("/pipayshopapi/item-commodity-category")
@Slf4j
public class ItemCommodityCategoryController {

    @Resource
    private ItemCommodityCategoryService categoryService;

    @PostMapping("secondCateList/{categoryPid}")
    @ApiOperation("二级分类列表展示")
    public ResponseVO secondCateList(@PathVariable Integer categoryPid){

        try{
            List<ItemCommodityCategory> secondCateList = categoryService.cateSecondList(categoryPid);
            return ResponseVO.getSuccessResponseVo(secondCateList);

        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("二级分类列表展示失败，请联系后台人员");
        }
    }

}
