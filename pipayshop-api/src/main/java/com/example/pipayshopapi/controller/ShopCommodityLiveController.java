package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ShopCommodityLive;
import com.example.pipayshopapi.entity.vo.InsertShopLiveVO;
import com.example.pipayshopapi.entity.vo.LivePageVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopCommodityLiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 实体店住的服务表 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@Api(value = "实体店住的服务接口",tags = "实体店住的服务接口")
@RestController
@RequestMapping("/pipayshopapi/shop-commodity-live")
public class ShopCommodityLiveController {

    @Resource
    private ShopCommodityLiveService shopCommodityLiveService;

    @GetMapping("selectShopCommodityLiveVO/{pages}/{limit}")
    @ApiOperation("查找实体店住的服务列表")
    public ResponseVO selectShopCommodityLiveVO(@PathVariable Integer pages, @PathVariable Integer limit){
        try {
            PageDataVO pageDataVO = shopCommodityLiveService.selectShopCommodityLiveVO(limit, pages);
            return ResponseVO.getSuccessResponseVo(pageDataVO.getList());
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("查找实体店住的服务列表失败，请联系后台人员");
        }
    }

    @GetMapping("selectShopLiveVOByCondition")
    @ApiOperation("条件筛选查找实体店住的服务列表")
    public ResponseVO selectShopLiveVOByCondition(LivePageVO livePageVO){
        try {
            PageDataVO pageDataVO = shopCommodityLiveService.selectShopLiveVOByCondition(livePageVO);
            return ResponseVO.getSuccessResponseVo(pageDataVO);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("条件筛选查找实体店住的服务列表失败，请联系后台人员");
        }
    }

    @GetMapping("selectShopLiveById/{commodityId}")
    @ApiOperation("根据服务id查找服务的详情信息")
    public ResponseVO selectShopLiveById(@PathVariable String commodityId){
        try {
            ShopCommodityLive shopCommodityLive = shopCommodityLiveService.selectShopLiveById(commodityId);
            return ResponseVO.getSuccessResponseVo("根据服务id查找服务的详情信息成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据服务id查找服务的详情信息失败，请联系后台人员");
        }
    }

    @PostMapping("insertShopLive")
    @ApiOperation("发布实体店住的服务")
    public ResponseVO insertShopLive(@RequestBody ShopCommodityLive shopCommodityLive){
        try {
            boolean result = shopCommodityLiveService.insertShopLive(shopCommodityLive);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("发布实体店住的服务成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("发布实体店住的服务失败，请联系后台人员");
        }
    }

    @PostMapping("updateShopLive")
    @ApiOperation("根据服务id更改服务信息")
    public ResponseVO updateShopLive(@RequestBody ShopCommodityLive shopCommodityLive){
        try {
            boolean result = shopCommodityLiveService.updateShopLive(shopCommodityLive);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据服务id更改服务信息成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据服务id更改服务信息失败，请联系后台人员");
        }
    }

}
