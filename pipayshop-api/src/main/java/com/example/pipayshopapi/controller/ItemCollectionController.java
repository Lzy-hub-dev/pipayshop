package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ItemCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 网店商品收藏表	 前端控制器
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
@RestController
@RequestMapping("/pipayshopapi/item-collection")
@Api(value = "网店商品收藏",tags = "网店商品收藏")
public class ItemCollectionController {

    @Resource
    ItemCollectionService itemCollectionService;

    /**
     * 网店商品收藏新增
     */
    @PostMapping("AddItemCommodityToCollection/{userId}/{commodityId}")
    @ApiOperation("网店商品收藏新增接口")
    public ResponseVO<String> AddItemCommodityToCollection(@PathVariable String userId, @PathVariable String commodityId) {
        try {
            int insert = itemCollectionService.AddItemCommodityToCollection(userId, commodityId);
            if (insert < 1){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("收藏成功");
        } catch (Exception e) {
            throw new BusinessException("收藏失败，请联系后台人员");
        }
    }


    /**
     *网店商品收藏删除
     */
    @PostMapping("closeItemCommodityToCollection/{userId}/{commodityId}")
    @ApiOperation("网店商品收藏删除接口")
    public ResponseVO<String> closeItemCommodityToCollection(@PathVariable String userId, @PathVariable String commodityId) {
        try {
            int update = itemCollectionService.closeItemCommodityToCollection(userId, commodityId);
            if (update < 1){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("取消收藏成功");
        } catch (Exception e) {
            throw new BusinessException("取消收藏失败，请联系后台人员");
        }
    }

    /**
     * 校验该网店商品是否已经被收藏
     */
    @GetMapping("isItemCommodityToCollection/{userId}/{commodityId}")
    @ApiOperation("校验该网店商品是否已经被收藏接口")
    public ResponseVO<Boolean> isItemCommodityToCollection(@PathVariable String userId, @PathVariable String commodityId) {
        try {
            boolean flag = itemCollectionService.isItemCommodityToCollection(userId, commodityId);
            return ResponseVO.getSuccessResponseVo(flag);
        } catch (Exception e) {
            throw new BusinessException("取消收藏失败，请联系后台人员");
        }
    }
}
