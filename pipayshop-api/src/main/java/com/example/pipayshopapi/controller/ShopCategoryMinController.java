package com.example.pipayshopapi.controller;

import com.example.pipayshopapi.entity.ShopCategoryMin;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopCategoryMinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 实体店三级分类表 前端控制器
 * @author wzx
 */
@Api(value = "实体店三级分类",tags = "实体店三级分类")
@RestController
@RequestMapping("/pipayshopapi/shop-category-min")
public class ShopCategoryMinController {

    @Resource
    ShopCategoryMinService shopCategoryMinService;

    private static final Logger log = LoggerFactory.getLogger(ShopCategoryMinController.class);

    @GetMapping("getShopCategoryMinList/{categoryPid}")
    @ApiOperation("实体店三级分类标签列表展示")
    public ResponseVO<List<ShopCategoryMin>> getShopCategoryMinList(@PathVariable String categoryPid) {
        try {
            List<ShopCategoryMin> list = shopCategoryMinService.getShopCategoryMinList(categoryPid);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }

    @GetMapping("getShopInfoMinListByCondition/{limit}/{pages}/{categoryId}")
    @ApiOperation("三级分类标签列表对应的店铺列表条件分页展示")
    public ResponseVO<PageDataVO> getShopInfoMinListByCondition(@PathVariable Integer limit, @PathVariable Integer pages, @PathVariable String categoryId){
        try {
            PageDataVO shopInfoMinListByCondition = shopCategoryMinService.getShopInfoMinListByCondition(limit, pages, categoryId);
            if (shopInfoMinListByCondition==null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(shopInfoMinListByCondition);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据条件筛选后获取实体店列表失败，请联系后台人员");
        }
    }
}