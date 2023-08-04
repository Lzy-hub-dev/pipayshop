package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ItemCommodityHistory;
import com.example.pipayshopapi.entity.ShopCommodityHistory;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityListVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopCommodityHistoryService;
import com.example.pipayshopapi.service.ShopCommodityInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 实体店用户商品历史记录表 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@RestController
@Slf4j
@Api(value = "实体店用户商品历史记录表",tags = "实体店用户商品历史记录表")
@RequestMapping("/pipayshopapi/shop-commodity-history")
public class ShopCommodityHistoryController {
    @Resource
    private ShopCommodityHistoryService shopCommodityHistoryService;

    @Resource
    private ShopCommodityInfoService shopCommodityService;
    @PostMapping("deleteHistory")
    @ApiOperation("删除用户浏览商品的历史记录-实体店")
    public ResponseVO deleteHistory(String userId,
                                    String commodityId) {

        try {
            boolean flag = shopCommodityHistoryService.deleteHistory(userId, commodityId);
            if (!flag) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("删除成功");
        } catch (Exception e) {
            // log.error(e.getMessage());
            throw new BusinessException("删除失败，请联系后台人员");
        }
    }
    @PostMapping("addHistory")
    @ApiOperation("新增用户浏览网店商品的历史记录")
    public ResponseVO addHistory(ShopCommodityHistory shopCommodityHistory){
        try {
            boolean result = shopCommodityHistoryService.addHistory(shopCommodityHistory);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("新增用户浏览商品的历史记录成功-实体店");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("新增用户浏览商品的历史记录失败，请联系后台人员");
        }
    }
    @GetMapping("history/{userId}")
    @ApiOperation("根据用户id查询用户浏览商品历史-实体店")
    public ResponseVO<List<ShopCommodityListVO>> historyList(@PathVariable("userId") String userId) {
        try {
            List<ShopCommodityListVO> list = shopCommodityService.historyList(userId);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("查询失败，请联系后台人员");
        }
    }
}
