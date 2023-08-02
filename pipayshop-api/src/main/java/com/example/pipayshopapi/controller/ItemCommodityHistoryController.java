package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ItemCommodityHistory;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ItemCommodityHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 网店用户商品历史记录表 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@RestController
@Api(value = "网店用户商品历史记录表",tags = "网店用户商品历史记录表")
@Slf4j
@RequestMapping("/pipayshopapi/item-commodity-history")
public class ItemCommodityHistoryController {

    @Resource
    private ItemCommodityHistoryService itemCommodityHistoryService;

    @PostMapping("addHistory")
    @ApiOperation("新增用户浏览网店商品的历史记录")
    public ResponseVO addHistory(ItemCommodityHistory itemCommodityHistory){
        try {
            boolean result = itemCommodityHistoryService.addHistory(itemCommodityHistory);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("新增用户浏览网店商品的历史记录成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("新增用户浏览网店商品的历史记录失败，请联系后台人员");
        }
    }

    @PostMapping("deleteHistory/{userId}/{commodityId}")
    @ApiOperation("删除用户浏览网店商品的历史记录")
    public ResponseVO deleteHistory(@PathVariable  String userId,@PathVariable String commodityId){
        try {
            boolean result = itemCommodityHistoryService.deleteHistory(userId, commodityId);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("删除用户浏览网店商品的历史记录成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("删除用户浏览网店商品的历史记录失败，请联系后台人员");
        }
    }


}
