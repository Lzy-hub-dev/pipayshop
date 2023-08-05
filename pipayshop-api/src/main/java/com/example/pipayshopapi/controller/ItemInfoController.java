package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.vo.ItemEvaluateVO;
import com.example.pipayshopapi.entity.vo.ItemInfoVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ItemInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@RequestMapping("/pipayshopapi/item-info")
@Api(value = "网店信息接口", tags = "网店信息接口")
@RestController
@Slf4j
public class ItemInfoController {

    @Resource
    private ItemInfoService itemInfoService;

    @GetMapping("getItemInfo/{itemId}/{page}/{limit}/{price}")
    @ApiOperation("根据网店id获取网店信息")
    public ResponseVO getItemInfo(@PathVariable String itemId,@PathVariable Integer page,@PathVariable Integer limit,@PathVariable Boolean price) {
        try {
            ItemInfoVO itemInfo = itemInfoService.getItemInfo(itemId,page,limit,price);
            if (itemInfo == null) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(itemInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("获取网店信息失败，请联系后台人员");
        }
    }

    @GetMapping("getItemEvaluate/{itemId}/{page}/{limit}")
    @ApiOperation("根据网店id获取网店评价信息")
    public ResponseVO getItemEvaluate(@PathVariable String itemId,@PathVariable Integer page,@PathVariable Integer limit) {
        try {
            ItemEvaluateVO itemEvaluate = itemInfoService.getItemEvaluate(itemId,page,limit);
            if (itemEvaluate == null) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(itemEvaluate);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("获取网店信息失败，请联系后台人员");
        }
    }

    @GetMapping("getItemCountByUserId/{userId}")
    @ApiOperation("根据用户id获取网店数量")
    public ResponseVO getItemCountByUserId(@PathVariable String userId) {
        try {
            Integer count = itemInfoService.getItemCountByUserId(userId);
            return ResponseVO.getSuccessResponseVo(count);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("获取网店信息失败，请联系后台人员");
        }
    }

    @GetMapping("getItemAddressById/{itemId}")
    @ApiOperation("根据网店id获取网店地址")
    public ResponseVO getItemAddressById(String itemId) {
        try {
            String itemAddressById = itemInfoService.getItemAddressById(itemId);
            return ResponseVO.getSuccessResponseVo(itemAddressById);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("根据网店id获取网店地址失败，请联系后台人员");
        }
    }

    @GetMapping("getItemInfoByUid/{userId}")
    @ApiOperation("根据用户id获取网店信息")
    public ResponseVO getItemInfoByUid(@PathVariable String userId) {
        try {
            List<ItemInfoVO> itemInfo = itemInfoService.getItemInfoByUid(userId);
            return ResponseVO.getSuccessResponseVo(itemInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("获取网店信息失败，请联系后台人员");
        }
    }

    @GetMapping("followList/{userId}")
    @ApiOperation("根据用户id查询 用户关注的网店列表")
    public ResponseVO followList(@PathVariable("userId") String userId) {
        try {
            List<ItemInfo> list = itemInfoService.getFollowList(userId);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("查询失败，请联系后台人员");
        }
    }

    @PostMapping("upVip/{userId}")
    @ApiOperation("根据用户id-网店-升级vip")
    public ResponseVO upVip(@PathVariable("userId") String userId) {
        try {
            boolean b = itemInfoService.upVip(userId);
            if (!b) {
                return ResponseVO.getFalseResponseVo("升级失败");
            }
            return ResponseVO.getSuccessResponseVo("升级成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("升级失败，请联系后台人员");
        }
    }

    @GetMapping("isVip/{userId}")
    @ApiOperation("根据用户id-判断对应网店是否vip")
    public ResponseVO isVip(@PathVariable("userId") String userId) {
        try {
            boolean b = itemInfoService.isVip(userId);
            return ResponseVO.getSuccessResponseVo(b);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("查询会员标识失败，请联系后台人员");
        }
    }
}
