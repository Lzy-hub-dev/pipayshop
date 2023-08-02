package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.dto.ApplyItemCommodityDTO;
import com.example.pipayshopapi.entity.dto.ItemSearchConditionDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ItemCommodityHistoryService;
import com.example.pipayshopapi.service.ItemCommodityInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 网店的商品表 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "网店商品接口", tags = "网店商品接口")
@RestController
@RequestMapping("/pipayshopapi/item-commodity-info")
@Slf4j
public class ItemCommodityInfoController {


    @Resource
    private ItemCommodityInfoService commodityInfoService;
    @Resource
    private ItemCommodityHistoryService itemCommodityHistoryService;


    @GetMapping("commodityOfCateList")
    @ApiOperation("商品某二级分类下的商品列表分页展示")
    public ResponseVO<PageDataVO> commodityOfCateList(@ModelAttribute CommodityPageVO commodityPageVO) {
        try {
            PageDataVO pageDataVO = commodityInfoService.commodityOfCateList(commodityPageVO);
            if (pageDataVO == null) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(pageDataVO);
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
            throw new BusinessException("某二级分类下的商品列表分页展示失败，请联系后台人员");
        }
    }

    @PostMapping("issueItemCommodity")
    @ApiOperation("发布网店商品")
    public ResponseVO issueItemCommodity(@RequestParam("files") MultipartFile[] files, ApplyItemCommodityDTO applyItemCommodityDTO){

        try {
            boolean result = commodityInfoService.issueItemCommodity(applyItemCommodityDTO,files);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("发布网店商品成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("发布网店商品失败，请联系后台人员");
        }
    }


    @GetMapping("itemSearchCommodity")
    @ApiOperation("网店首页条件过滤列表展示")
    public ResponseVO<PageDataVO> itemSearchCommodity(ItemSearchConditionDTO itemSearchConditionDTO) {
        try {
            PageDataVO pageDataVO = commodityInfoService.itemSearchCommodity(itemSearchConditionDTO);
            if (pageDataVO == null) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(pageDataVO);
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
            throw new BusinessException("网店首页条件搜索失败，请联系后台人员");
        }
    }


    @GetMapping("itemCommodityChoose/{itemId}/{brandId}")
    @ApiOperation("获取同一网店同一品牌的商品接口")
    public ResponseVO<List<CommodityVO>> itemCommodityChoose(@PathVariable("itemId") String itemId, @PathVariable("brandId") String brandId) {
        try {
            List<CommodityVO> commodityVOS = commodityInfoService.itemCommodityChoose(itemId, brandId);
            if (commodityVOS == null) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(commodityVOS);
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
            throw new BusinessException("获取同一网店同一品牌的商品失败，请联系后台人员");
        }
    }

    @GetMapping("itemCommodityDetail/{commodityId}/{userId}")
    @ApiOperation("获取网店商品详情接口")
    public ResponseVO<CommodityDetailVO> itemCommodityDetail(@PathVariable("commodityId") String commodityId,
                                                             @PathVariable("userId")String userId) {
        try {

            CommodityDetailVO commodityDetailVO = commodityInfoService.itemCommodityDetail(commodityId,userId);
            if (commodityDetailVO == null) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(commodityDetailVO);
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
            throw new BusinessException("获取网店商品详情接口失败，请联系后台人员");
        }
    }

    @GetMapping("collectList/{userId}")
    @ApiOperation("根据用户id查询 用户收藏的商品列表")
    public ResponseVO collectList(@PathVariable("userId") String userId) {
        try {
            List<ItemCommodityInfo> list = commodityInfoService.getCollectList(userId);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("查询失败，请联系后台人" + "、员");
        }
    }

    @GetMapping("followList/{userId}")
    @ApiOperation("根据用户id查询 用户关注的网店列表")
    public ResponseVO followList(@PathVariable("userId") String userId) {
        try {
            List<ItemInfo> list = commodityInfoService.getFollowList(userId);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("查询失败，请联系后台人" + "、员");
        }
    }

    @GetMapping("history/{userId}")
    @ApiOperation("根据用户id查询用户浏览商品历史-网店")
    public ResponseVO historyList(@PathVariable("userId") String userId) {
        try {
            List<ItemCommodityInfoVO> list = commodityInfoService.historyList(userId);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("查询失败，请联系后台人" + "、员");
        }
    }

    @GetMapping("examineCommodityList/{userId}")
    @ApiOperation("根据卖家id查询网店的商品审核列表")
    public ResponseVO examineCommodityList(@PathVariable("userId") String userId,
                                           @RequestParam
                                           @ApiParam(value = "0:审核中;1:审核通过")
                                           Integer examineStatus) {
        try {
            List<ItemCommodityInfoVO> list = commodityInfoService.examineCommodityList(userId, examineStatus);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("查询失败，请联系后台人" + "、员");
        }
    }

    @GetMapping("commodityList/{itemId}")
    @ApiOperation("根据网店id查询网店的商品列表")
    public ResponseVO commodityList(@PathVariable("itemId") String itemId) {
        try {
            ItemInfoVO vo = commodityInfoService.commodityList(itemId);
            if (vo == null) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(vo);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("查询失败，请联系后台人" + "、员");
        }
    }

    @PostMapping("changeCommodityStatus/{commodity}/{status}")
    @ApiOperation("根据商品id上架/下架 商品")
    public ResponseVO changeCommodityStatus(@PathVariable("commodity") String commodity,
                                            @PathVariable("status")
                                            @ApiParam("1:上架;2:下架")
                                            String status) {
        try {
            boolean flag = commodityInfoService.changeCommodityStatus(commodity, status);
            if (!flag) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("查询失败，请联系后台人" + "、员");
        }
    }

    @PostMapping("deleteHistory")
    @ApiOperation("删除用户浏览网店商品的历史记录")
    public ResponseVO deleteHistory( String userId,
                                    String commodityId) {

        try {
            boolean flag = itemCommodityHistoryService.deleteHistory(userId, commodityId);
            if (!flag) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("删除，请联系后台人" + "、员");
        }
    }


}
