package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.dto.ItemSearchConditionDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ItemCommodityInfoService;
import com.example.pipayshopapi.service.ShopCommodityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 网店的商品表 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "商品接口",tags = "商品接口")
@RestController
@RequestMapping("/pipayshopapi/item-commodity-info")
@Slf4j
public class ItemCommodityInfoController {


    @Resource
    private ItemCommodityInfoService commodityInfoService;


    @GetMapping("commodityOfCateList")
    @ApiOperation("某二级分类下的商品列表分页展示")
    public ResponseVO<PageDataVO> commodityOfCateList(@ModelAttribute commodityPageVO commodityPageVO) {
        try {
            PageDataVO pageDataVO = commodityInfoService.commodityOfCateList(commodityPageVO);
            if (pageDataVO == null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(pageDataVO);
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
            throw new BusinessException("某二级分类下的商品列表分页展示失败，请联系后台人员");
        }
    }


    @GetMapping("itemSearchCommodity")
    @ApiOperation("网店首页条件搜索")
    public ResponseVO<PageDataVO> itemSearchCommodity(ItemSearchConditionDTO itemSearchConditionDTO) {
        try {
            PageDataVO pageDataVO = commodityInfoService.itemSearchCommodity(itemSearchConditionDTO);
            if (pageDataVO == null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(pageDataVO);
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
            throw new BusinessException("网店首页条件搜索失败，请联系后台人员");
        }
    }


    @GetMapping("itemCommodityChoose/{itemId}/{brandId}")
    @ApiOperation("获取同一网店同一品牌的商品接口")
    public ResponseVO<List<commodityVO>> itemCommodityChoose(@PathVariable("itemId") String itemId,@PathVariable("brandId") String brandId) {
        try {
            List<commodityVO> commodityVOS = commodityInfoService.itemCommodityChoose(itemId, brandId);
            if (commodityVOS == null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(commodityVOS);
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
            throw new BusinessException("获取同一网店同一品牌的商品失败，请联系后台人员");
        }
    }

    @GetMapping("itemCommodityDetail/{commodityId}")
    @ApiOperation("获取网店商品详情接口")
    public ResponseVO<CommodityDetailVO> itemCommodityDetail(@PathVariable("commodityId") String commodityId) {
        try {

            CommodityDetailVO commodityDetailVO = commodityInfoService.itemCommodityDetail(commodityId);
            if (commodityDetailVO == null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(commodityDetailVO);
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
            throw new BusinessException("获取网店商品详情接口失败，请联系后台人员");
        }
    }


}
