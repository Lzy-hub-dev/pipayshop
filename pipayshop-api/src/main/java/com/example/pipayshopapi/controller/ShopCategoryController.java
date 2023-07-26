package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ShopCategory;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.service.ShopCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private ShopCategoryService categoryService;

    private static final Logger log = LoggerFactory.getLogger(ShopCategoryController.class);

    @GetMapping("getShopCategoryList")
    @ApiOperation("查询一级分类对应的二级分列表")
    public ResponseVO getShopCategoryList(Integer pageNum, Integer pageSize,Integer topId) {
        try {
            PageDataVO list = categoryService.getShopCategoryList(pageNum, pageSize,topId);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }
    @GetMapping("getShopCategoryById/{categoryId}")
    @ApiOperation("根据分类id查询分类")
    public ResponseVO getShopCategoryById(@PathVariable String categoryId) {
        try {
            ShopCategory category = categoryService.getShopCategoryById(categoryId);
            return category==null?ResponseVO.getFalseResponseVo(null):ResponseVO.getSuccessResponseVo(category);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }
    @PostMapping("deleteShopCategoryById/{categoryId}")
    @ApiOperation("根据分类id删除分类")
    public ResponseVO deleteShopCategoryById(@PathVariable String categoryId) {
        try {
            Boolean aBoolean = categoryService.deleteShopCategoryById(categoryId);
            return aBoolean ? ResponseVO.getSuccessResponseVo(null) : ResponseVO.getFalseResponseVo(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }

}
