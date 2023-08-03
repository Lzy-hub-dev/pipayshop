package com.example.pipayshopapi.controller;

import com.example.pipayshopapi.entity.vo.IndexShopInfoVO;
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
@Api(value = "实体店二级分类", tags = "实体店二级分类")
@RestController
@RequestMapping("/pipayshopapi/shop-category")
public class ShopCategoryController {

    @Resource
    private ShopCategoryService shopCategoryService;
    @Resource
    private ShopInfoService shopInfoService;
    @GetMapping("getShopCategorySecList/{categoryPid}/{pageNum}/{pageSize}")
    @ApiOperation("查询二级分类列表")
    public ResponseVO<PageDataVO> getShopCategorySecList(@PathVariable String categoryPid,
                                             @PathVariable Integer pageNum,
                                             @PathVariable Integer pageSize) {
        try {
            PageDataVO list = shopCategoryService.getShopCategorySecList(categoryPid,pageNum, pageSize);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            return ResponseVO.getFalseResponseVo(null);
        }

    }
    @GetMapping("getSecShopInfoListByCondition/{limit}/{pages}/{categoryId}/{state}")
    @ApiOperation("根据条件筛选后获取实体店列表")
    public ResponseVO<List<IndexShopInfoVO>> getSecShopInfoListByCondition(@PathVariable Integer limit, @PathVariable Integer pages, @PathVariable String categoryId, @PathVariable Integer state){
        try {
            List<IndexShopInfoVO> list = shopInfoService.getSecShopInfoListByCondition(limit,pages,categoryId,state);
            if (list==null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(list);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据条件筛选后获取实体店列表失败，请联系后台人员");
        }
    }
}
