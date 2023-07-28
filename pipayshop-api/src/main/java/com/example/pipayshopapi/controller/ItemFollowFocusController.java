package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.ItemInfoVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ItemFollowFocusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 粉丝关注表 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */

@RequestMapping("/pipayshopapi/item-follow-focus")
@Api(value = "网店关注接口",tags = "网店关注接口")
@RestController
@Slf4j
public class ItemFollowFocusController {
    @Resource
    private ItemFollowFocusService followFocusService;

    @GetMapping("userFollowItem/{followId}/{itemId}")
    @ApiOperation("关注网店")
    public ResponseVO userFollowItem(@PathVariable String followId,@PathVariable String itemId) {
        try {
            Boolean update = followFocusService.userFollowItem(followId, itemId);
            if (! update ){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("关注网店成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("关注网店失败，请联系后台人" + "、员");
        }
    }
}
