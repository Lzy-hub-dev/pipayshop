package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ShopCategoryTop;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.service.ShopCategoryTopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class ShopCategoryTopController {
    @Autowired
    private ShopCategoryTopService categoryTopService;

    private static final Logger log = LoggerFactory.getLogger(ShopCategoryTopController.class);



    @GetMapping("getShopCategoryTopList")
    @ApiOperation("查询一级分类列表")
    public ResponseVO getShopCategoryTopList(Integer pageNum, Integer pageSize) {
        try {
            PageDataVO list = categoryTopService.getShopCategoryTopList(pageNum, pageSize);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }

    @GetMapping("getAllShopCategoryTopList")
    @ApiOperation("获取所有一级分类列表")
    public ResponseVO getAllShopCategoryTopList() {
        try {
            List<ShopCategoryTop> allShopCategoryTopList = categoryTopService.getAllShopCategoryTopList();
            return ResponseVO.getSuccessResponseVo(allShopCategoryTopList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo("获取所有一级分类失败");
        }
    }
    @GetMapping("getShopCategoryTopById/{categoryTopId}")
    @ApiOperation("根据分类id查询分类")
    public ResponseVO getShopCategoryTopById(@PathVariable String categoryTopId) {
        try {
            ShopCategoryTop categoryTop = categoryTopService.getShopCategoryTopById(categoryTopId);
            return categoryTop==null?ResponseVO.getFalseResponseVo(null):ResponseVO.getSuccessResponseVo(categoryTop);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }
    @PostMapping("deleteShopCategoryTopById/{categoryTopId}")
    @ApiOperation("根据分类id删除分类")
    public ResponseVO deleteShopCategoryTopById(@PathVariable String categoryTopId) {
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
    public ResponseVO updateShopCategoryTopById(@RequestBody ShopCategoryTop shopCategoryTop) {
        try {
            Boolean aBoolean = categoryTopService.updateShopCategoryTopById(shopCategoryTop);
            return aBoolean ? ResponseVO.getSuccessResponseVo(null) : ResponseVO.getFalseResponseVo(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }

}
