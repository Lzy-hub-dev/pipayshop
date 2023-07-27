package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.ItemInfoVO;
import com.example.pipayshopapi.entity.vo.OrderPageVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ItemInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@RequestMapping("/pipayshopapi/item-info")
@Api(value = "网店信息接口",tags = "网店信息接口")
@RestController
@Slf4j
public class ItemInfoController {

    @Resource
    private ItemInfoService itemInfoService;
    @GetMapping("getItemInfo/{commodityId}")
    @ApiOperation("获取网店信息")
    public ResponseVO getItemInfo(@PathVariable String commodityId) {
        try {
            ItemInfoVO itemInfo = itemInfoService.getItemInfo(commodityId);
            if (itemInfo == null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(itemInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("获取网店信息失败，请联系后台人员");
        }
    }
}
