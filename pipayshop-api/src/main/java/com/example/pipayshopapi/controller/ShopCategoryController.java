package com.example.pipayshopapi.controller;

import com.example.pipayshopapi.entity.ShopCategory;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopCategoryService;
import com.example.pipayshopapi.service.ShopInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 实体店二级分类表 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "二级分类", tags = "二级分类")
@RestController
@RequestMapping("/pipayshopapi/shop-category")
public class ShopCategoryController {

    @Resource
    private ShopCategoryService shopCategoryService;
    @Resource
    private ShopInfoService shopInfoService;
    @GetMapping("getShopCategorySecList/{categoryPid}")
    @ApiOperation("查询二级分类列表")
    public ResponseVO<List<ShopCategory>> getShopCategorySecList(@PathVariable String categoryPid) {
        try {
            List<ShopCategory> list = shopCategoryService.getShopCategorySecList(categoryPid);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            return ResponseVO.getFalseResponseVo(null);
        }

    }
    @GetMapping("getSecShopInfoListByCondition/{limit}/{pages}/{categoryId}")
    @ApiOperation("根据一级分类-获取所有列表")
    public ResponseVO<PageDataVO> getSecShopInfoListByCondition(@PathVariable Integer limit, @PathVariable Integer pages, @PathVariable String categoryId){
        try {
            PageDataVO secShopInfoListByCondition = shopInfoService.getSecShopInfoListByCondition(limit, pages, categoryId);

            return ResponseVO.getSuccessResponseVo(secShopInfoListByCondition);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据一级分类-获取所有实体店列表失败，请联系后台人员");
        }
    }
}
