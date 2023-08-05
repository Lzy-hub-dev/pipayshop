package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopFollowFocusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@Api(value = "实体店title接口",tags = "实体店关注接口")
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

    @GetMapping("getFollowList/{shopId}/{pageNum}/{pageSize}")
    @ApiOperation("获取实体店的粉丝列表")
    public ResponseVO<PageDataVO> getFollowList(@PathVariable String shopId,
                                    @ApiParam("当前页")
                                    @PathVariable Integer pageNum,
                                    @ApiParam("展示条数")
                                    @PathVariable Integer pageSize){
        try{
            PageDataVO result = followFocusService.getFollowList(shopId,pageNum,pageSize);
            return ResponseVO.getSuccessResponseVo(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("粉丝列表获取失败，请联系后台人员");
        }
    }

    /**
     * 校验是否已经关注该店铺
     */
    @GetMapping("isFollowShop/{shopId}/{userId}")
    @ApiOperation("校验是否已经关注该店铺")
    public ResponseVO<Boolean> isFollowShop(@PathVariable String shopId, @PathVariable String userId){
        try{
            boolean result = followFocusService.isFollowShop(shopId,userId);
            return ResponseVO.getSuccessResponseVo(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("校验是否已经关注该店铺失败，请联系后台人员");
        }
    }

    /**
     * 根据shopId查找店铺的粉丝数量
     */
    @PostMapping("SelectFollowShopSum/{shopId}")
    @ApiOperation("根据shopId查找店铺的粉丝数量")
    public ResponseVO<Integer> SelectFollowShopSum(@PathVariable String shopId){
        try{
            Integer result = followFocusService.SelectFollowShopSum(shopId);
            return ResponseVO.getSuccessResponseVo(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据shopId查找店铺的粉丝数量失败，请联系后台人员");
        }
    }
    @GetMapping("followList/{userId}")
    @ApiOperation("根据用户id-查询-关注-实体店列表")
    public ResponseVO<List<ShopInfo>> followList(@PathVariable("userId") String userId) {
        try {
            List<ShopInfo> list = followFocusService.getFollowList(userId);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("查询失败，请联系后台人" + "、员");
        }
    }
}
