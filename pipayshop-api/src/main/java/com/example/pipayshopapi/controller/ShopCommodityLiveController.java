package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ShopCommodityLive;
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

//    @RequestParam("checkInTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date checkInTime
//    @RequestParam("departureTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date departureTime
    @GetMapping("selectShopLiveVOByCondition/{pages}/{limit}/{checkInTime}/{departureTime}/{adult}/{children}")
    @ApiOperation("条件筛选查找实体店住的服务列表")
    public ResponseVO selectShopLiveVOByCondition(@PathVariable Integer pages, @PathVariable Integer limit,
                                                  @RequestParam("checkInTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date checkInTime,
                                                  @RequestParam("departureTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date departureTime,
                                                  @PathVariable Integer adult,@PathVariable Integer children){
        try {
            PageDataVO pageDataVO = shopCommodityLiveService.selectShopLiveVOByCondition(limit, pages,checkInTime, departureTime, adult, children);
            return ResponseVO.getSuccessResponseVo(pageDataVO.getList());
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



}
