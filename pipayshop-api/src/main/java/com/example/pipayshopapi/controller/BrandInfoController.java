package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.entity.vo.ShopCategoryVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.BrandInfoService;
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
 *  品牌表
 * </p>
 *
 * @author nws
 * @since 2023-07-26
 */
@Api(value = "品牌接口",tags = "品牌接口")
@RestController
@RequestMapping("/pipayshopapi/brand-info")
public class BrandInfoController {
    @Resource
    private BrandInfoService brandInfoService;

    @GetMapping("selectAllBrandList")
    @ApiOperation("查找品牌分类列表")
    public ResponseVO<List> selectAllBrandList(){
        try {
            List<ShopCategoryVO> list = brandInfoService.selectAllBrandList();
            if (list==null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(list);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("查找品牌分类列表失败，请联系后台人员");
        }
    }

}
