package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopCollectionService;
import com.example.pipayshopapi.service.ShopCommodityInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author wzx
 */
@RestController
@RequestMapping("/pipayshopapi/shop-collection")
@Api(value = "实体店服务收藏接口(最终版)",tags = "实体店服务收藏接口(最终版)")
public class ShopCollectionController {

    @Resource
    private ShopCommodityInfoService shopCommodityService;
    @Resource
    private ShopCollectionService shopCollectionService;

    /**
     * 服务收藏新增
     */
    @PostMapping("AddShopCollection/{userId}/{commodityId}")
    @ApiOperation("服务收藏新增接口")
    public ResponseVO<String> addShopCollection(@PathVariable String userId, @PathVariable String commodityId) {
        try {
            int insert = shopCollectionService.addShopCollection(userId, commodityId);
            if (insert < 1){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("收藏成功");
        } catch (Exception e) {
            throw new BusinessException("收藏失败，请联系后台人员");
        }
    }

    /**
     * 服务收藏删除
     */
    @PostMapping("removeShopCollection/{userId}/{commodityId}")
    @ApiOperation("服务收藏删除接口")
    public ResponseVO<String> removeShopCollection(@PathVariable String userId, @PathVariable String commodityId) {
        try {
            //置1
            int update = shopCollectionService.removeShopCollection(userId, commodityId);
            if (update < 1){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("取消收藏成功");
        } catch (Exception e) {
            throw new BusinessException("取消收藏失败，请联系后台人员");
        }
    }


    /**
     * 校验服务是否已经被收藏
     */
    @GetMapping("isCommodityToCollection/{userId}/{commodityId}")
    @ApiOperation("校验服务是否已经被收藏接口")
    public ResponseVO<Boolean> isCommodityToCollection(@PathVariable String userId, @PathVariable String commodityId) {
        try {
            boolean flag = shopCollectionService.isCommodityToCollection(userId, commodityId);
            return ResponseVO.getSuccessResponseVo(flag);
        } catch (Exception e) {
            throw new BusinessException("获取收藏信息失败，请联系后台人员");
        }
    }


    @GetMapping("collectList/{page}/{limit}/{userId}")
    @ApiOperation("根据用户id查询用户收藏的实体类商品列表")
    public ResponseVO<PageDataVO> collectList(@PathVariable Integer page,@PathVariable Integer limit,@PathVariable("userId") String userId) {
        try {
            PageDataVO collectList = shopCommodityService.getCollectList(page, limit, userId);
            return ResponseVO.getSuccessResponseVo(collectList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("查询失败，请联系后台人" + "、员");
        }
    }




}
