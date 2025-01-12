package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ShopCategoryTop;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.service.ShopCategoryTopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 实体店一级分类表 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "实体店一级分类",tags = "实体店一级分类")
@RestController
@RequestMapping("/pipayshopapi/shop-category-top")
@Slf4j
public class ShopCategoryTopController {
    @Resource
    private ShopCategoryTopService categoryTopService;


    @GetMapping("getShopCategoryTopList")
    @ApiOperation("查询一级分类列表")
    public ResponseVO<List<ShopCategoryTop>> getShopCategoryTopList() {
        try {
            List<ShopCategoryTop> list = categoryTopService.getShopCategoryTopList();
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }


    @PostMapping("deleteShopCategoryTopById/{categoryTopId}")
    @ApiOperation("根据分类id删除分类")
    public ResponseVO<Boolean> deleteShopCategoryTopById(@PathVariable String categoryTopId) {
        try {
            Boolean aBoolean = categoryTopService.deleteShopCategoryTopById(categoryTopId);
            return aBoolean ? ResponseVO.getSuccessResponseVo(null) : ResponseVO.getFalseResponseVo(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }
    @PostMapping("updateShopCategoryTopById")
    @ApiOperation("根据分类id修改分类")
    public ResponseVO<Boolean> updateShopCategoryTopById(@RequestBody ShopCategoryTop shopCategoryTop) {
        try {
            Boolean aBoolean = categoryTopService.updateShopCategoryTopById(shopCategoryTop);
            return aBoolean ? ResponseVO.getSuccessResponseVo(null) : ResponseVO.getFalseResponseVo(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }

}
