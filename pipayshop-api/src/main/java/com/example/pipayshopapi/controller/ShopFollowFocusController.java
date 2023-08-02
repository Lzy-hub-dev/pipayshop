package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.FollowFocus;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 粉丝关注表 前端控制器
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
@RestController
@Api(value = "实体店关注接口",tags = "实体店关注接口")
@RequestMapping("/pipayshopapi/shop-follow-focus")
public class ShopFollowFocusController {
    @Resource
    private ShopFollowFocusService followFocusService;

    @GetMapping("userFollowItem/{followId}/{shopId}")
    @ApiOperation("关注实体店")
    public ResponseVO userFollowItem(@PathVariable String followId,@PathVariable String shopId) {
        try {
            Boolean update = followFocusService.userFollowItem(followId, shopId);
            if (! update ){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("关注实体店成功");
        } catch (Exception e) {
            throw new BusinessException("关注网店失败，请联系后台人" + "、员");
        }
    }

    @PostMapping("userUnfollow/{followId}/{shopId}")
    @ApiOperation("用户取消关注")
    private ResponseVO userUnfollow(@PathVariable String followId,@PathVariable String shopId){
        try {
            Boolean result = followFocusService.userUnfollow(followId, shopId);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("用户取消关注成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("用户取消关注失败，请联系后台人员");
        }
    }


}
