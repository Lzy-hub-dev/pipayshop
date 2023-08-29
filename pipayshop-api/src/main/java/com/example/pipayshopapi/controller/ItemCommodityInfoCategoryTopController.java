package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ItemCommodityInfoCategoryTop;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ItemCommodityInfoCategoryTopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 网店商品的一级分类表 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "网店一级分类接口",tags = "网店一级分类接口")
@RestController
@RequestMapping("/pipayshopapi/item-commodity-info-category-top")
@Slf4j
public class ItemCommodityInfoCategoryTopController {


    @Resource
    private ItemCommodityInfoCategoryTopService categoryTopService;

    @PostMapping("topCateList")
    @ApiOperation("网店首级分类标签列表展示")
    public ResponseVO topCateList(){

        try{
            List<ItemCommodityInfoCategoryTop> topCateList = categoryTopService.cateTopList();
            return ResponseVO.getSuccessResponseVo(topCateList);

        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("首级分类列表展示失败，请联系后台人员");
        }
    }

}
