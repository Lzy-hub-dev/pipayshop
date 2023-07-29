package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopCommodityInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 * 实体店的商品表 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@Api(value = "实体店的商品表",tags = "实体店的商品表")
@RestController
@RequestMapping("/pipayshopapi/shop-commodity-info")
public class ShopCommodityInfoController {

    @Resource
    private ShopCommodityInfoService shopCommodityService;

    @PostMapping("issueShopCommodity")
    @ApiOperation("发布实体店商品")
    public ResponseVO issueShopCommodity(@RequestParam("commodityImgList") String commodityImgList, @RequestBody ShopCommodityVO shopCommodityVO){
        try {
            boolean result = shopCommodityService.issueShopCommodity(shopCommodityVO,commodityImgList);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("发布实体店商品成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("发布实体店商品失败，请联系后台人员");
        }
    }

    @GetMapping("selectShopInfoListByShopId/{pages}/{limit}/{shopId}")
    @ApiOperation("根据店铺id查找实体店商品的详情信息列表")
    public ResponseVO selectShopInfoListByShopId(@PathVariable Integer pages, @PathVariable Integer limit,@PathVariable String shopId){
        try {
            PageDataVO pageDataVO = shopCommodityService.selectShopInfoListByShopId(limit, pages, shopId);
            return ResponseVO.getSuccessResponseVo(pageDataVO.getList());
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据店铺id查找实体店商品的详情信息列表失败，请联系后台人员");
        }
    }

    @GetMapping("selectShopInfoByCommodityId/{commodityId}")
    @ApiOperation("根据商品的id查找实体店商品的详情信息")
    public ResponseVO selectShopInfoByCommodityId(@PathVariable String commodityId){
        try {
            ShopCommodityInfo shopCommodityInfo = shopCommodityService.selectShopInfoByCommodityId(commodityId);
            return ResponseVO.getSuccessResponseVo(shopCommodityInfo);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据商品的id查找实体店商品的详情信息失败，请联系后台人员");
        }

    }


}