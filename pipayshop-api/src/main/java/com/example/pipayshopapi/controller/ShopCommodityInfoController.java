package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.dto.ApplyShopCommodityDTO;
import com.example.pipayshopapi.entity.dto.ApplyShopDTO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopCommodityInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
@Api(value = "实体店商品",tags = "实体店商品")
public class ShopCommodityInfoController {

    @Resource
    private ShopCommodityInfoService shopCommodityService;

    @PostMapping("issueShopCommodity")
    @ApiOperation("发布实体店商品")
    public ResponseVO issueShopCommodity(ApplyShopCommodityDTO applyShopCommodityDTO, @RequestParam("files") MultipartFile[] files){

        try {
            boolean insert = shopCommodityService.issueShopCommodity(applyShopCommodityDTO, files);
            if (!insert){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("发布实体店商品成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("发布实体店商品失败，请联系后台人员");
        }
    }

}
