package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ShopEvaluate;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopEvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 实体店评价 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@Api(value = "实体店评价接口",tags = "实体店评价接口")
@RestController
@RequestMapping("/pipayshopapi/shop-evaluate")
public class ShopEvaluateController {

    @Resource
    private ShopEvaluateService shopEvaluateService;

    @GetMapping("getShopEvaluateListByItemId/{page}/{limit}/{itemId}")
    @ApiOperation("根据实体店id获取实体店评价列表")
    public ResponseVO<PageDataVO> getShopEvaluateListByItemId(@PathVariable Integer page,@PathVariable Integer limit,@PathVariable String itemId){
        try {
            PageDataVO pageList = shopEvaluateService.getShopEvaluateListByItemId(page, limit, itemId);
            return ResponseVO.getSuccessResponseVo(pageList);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据实体店id获取实体店评价列表失败，请联系后台人员");
        }
    }

    @PostMapping("addShopEvaluate")
    @ApiOperation("增加实体店评价")
    public ResponseVO<String> addShopEvaluate(@RequestBody ShopEvaluate shopEvaluate){
        try {
            boolean result = shopEvaluateService.addShopEvaluate(shopEvaluate);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("增加实体店评价成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("增加实体店评价失败，请联系后台人员");
        }
    }


    @PostMapping("deleteShopEvaluate/{evaluateId}/{userId}")
    @ApiOperation("删除实体店评价列表")
    public ResponseVO<String> deleteShopEvaluate(@PathVariable String evaluateId,@PathVariable String userId){
        try {
            boolean result = shopEvaluateService.deleteShopEvaluate(evaluateId, userId);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("删除实体店评价列表成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("删除实体店评价列表失败，请联系后台人员");
        }
    }
}
