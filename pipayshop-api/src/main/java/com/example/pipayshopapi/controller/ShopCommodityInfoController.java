package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopCommodityInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * <p>
 * 实体店的商品表 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@Controller
@RequestMapping("/pipayshopapi/shop-commodity-info")
public class ShopCommodityInfoController {

    @Resource
    private ShopCommodityInfoService shopCommodityService;

    @PostMapping("issueShopCommodity")
    @ApiOperation("发布实体店商品")
    public ResponseVO issueShopCommodity(@RequestParam("commodityImgList") String commodityImgList, @RequestBody ShopCommodityVO shopCommodityVO){
        System.out.println(commodityImgList);
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

}
