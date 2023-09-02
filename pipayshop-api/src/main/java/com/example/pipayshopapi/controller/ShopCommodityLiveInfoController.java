package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ShopCommodityLiveInfo;
import com.example.pipayshopapi.entity.dto.ShopCommodityLiveInfoListDTO;
import com.example.pipayshopapi.entity.dto.ShopHotelRecordDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopCommodityLiveInfoService;
import com.example.pipayshopapi.util.FileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 酒店的房型表 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-08-02
 */
@Api(value = "酒店的房型表接口",tags = "酒店的房型表接口")
@RestController
@RequestMapping("/pipayshopapi/shop-commodity-live-info")
public class ShopCommodityLiveInfoController {

    @Resource
    private ShopCommodityLiveInfoService shopCommodityLiveInfoService;

    @GetMapping("selectShopLiveByRoomId/{roomId}")
    @ApiOperation("根据房型id查找房型的详细信息")
    public ResponseVO<ShopCommodityLiveInfoVO> selectShopLiveByRoomId(@PathVariable String roomId){
        try {
            ShopCommodityLiveInfoVO shopCommodityLiveInfoVO = shopCommodityLiveInfoService.selectShopLiveByRoomId(roomId);
            return ResponseVO.getSuccessResponseVo(shopCommodityLiveInfoVO);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据房型id查找房型的详细信息失败，请联系后台人员");
        }
    }


    @PostMapping("roomTopImageUp")
    @ApiOperation("酒店头像图片上传")
    public ResponseVO<String> roomTopImageUp(MultipartFile multipartFile){
        try {
            String imageId = shopCommodityLiveInfoService.roomTopImageUp(multipartFile);
            return ResponseVO.getSuccessResponseVo(imageId);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("酒店头像图片上传失败，请联系后台人员");
        }
    }

    @PostMapping("roomImageUp")
    @ApiOperation("酒店轮播图上传")
    public ResponseVO<String> roomImageUp(MultipartFile multipartFile){
        try {
            String imageId = shopCommodityLiveInfoService.roomImageUp(multipartFile);
            return ResponseVO.getSuccessResponseVo(imageId);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("酒店轮播图上传失败，请联系后台人员");
        }
    }


    @PostMapping("insertShopLiveInfo")
    @ApiOperation("发布实体店酒店的服务")
    public ResponseVO<String> insertShopLiveInfo(@RequestBody ShopCommodityLiveInfoVO1 shopCommodityLiveInfo){
        try {
            boolean result = shopCommodityLiveInfoService.insertShopLiveInfo(shopCommodityLiveInfo);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("发布实体店酒店的服务成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("发布实体店酒店的服务信息失败，请联系后台人员");
        }
    }

    @PostMapping("updateShopLiveInfo")
    @ApiOperation("根据房型id更改房型的详细信息")
    public ResponseVO<String> updateShopLiveInfo(@RequestBody ShopCommodityLiveInfo shopCommodityLiveInfo){
        try {
            boolean result = shopCommodityLiveInfoService.updateShopLiveInfo(shopCommodityLiveInfo);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据房型id更改房型的详细信息成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据房型id更改房型的详细信息失败，请联系后台人员");
        }
    }

    @PostMapping("deleteShopLiveInfo/{roomId}")
    @ApiOperation("根据房型id删除房型的详细信息")
    public ResponseVO<String> deleteShopLiveInfo(@PathVariable String roomId){
        try {
            boolean result = shopCommodityLiveInfoService.deleteShopLiveInfo(roomId);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据房型id删除房型的详细信息成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据房型id删除房型的详细信息失败，请联系后台人员");
        }
    }


    @PostMapping("selectShopCommodityLiveInfoList")
    @ApiOperation("根据实体店id和入住时间和离店时间来搜索房型")
    public ResponseVO selectShopCommodityLiveInfoList(@RequestBody ShopCommodityLiveInfoListDTO shopCommodityLiveInfoListDTO){
        try {
            List<ShopCommodityLiveInfoListVO> shopCommodityLiveInfoListVOS = shopCommodityLiveInfoService.selectShopCommodityLiveInfoList(shopCommodityLiveInfoListDTO.getShopId(),shopCommodityLiveInfoListDTO.getStartTime(),shopCommodityLiveInfoListDTO.getEndTime());
            return ResponseVO.getSuccessResponseVo(shopCommodityLiveInfoListVOS);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据房型id删除房型的详细信息失败，请联系后台人员");
        }
    }


    @PostMapping("selectShopEvaluateCount/{shopId}")
    @ApiOperation("获取实体店酒店评价数")
    public ResponseVO selectShopEvaluateCount(@PathVariable String shopId){
        try {
            Integer count = shopCommodityLiveInfoService.selectShopEvaluateCount(shopId);
            return ResponseVO.getSuccessResponseVo(count);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("获取实体店酒店评价数失败，请联系后台人员");
        }
    }

    @PostMapping("applyShopCommodityLive")
    @ApiOperation("提交入住酒店")
    public ResponseVO applyShopCommodityLive(@RequestBody ShopHotelRecordDTO shopHotelRecordDTO){
        try {
            boolean update = shopCommodityLiveInfoService.applyShopCommodityLive(shopHotelRecordDTO);
            if (!update){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("提交入住酒店成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("获取实体店酒店评价数失败，请联系后台人员");
        }
    }

    @GetMapping("selectShopCommodityLiveVO/{pages}/{limit}")
    @ApiOperation("查找实体店酒店的服务列表")
    public ResponseVO selectShopCommodityLiveInfoVO(@PathVariable Integer pages, @PathVariable Integer limit){
        try {
            PageDataVO pageDataVO = shopCommodityLiveInfoService.selectShopCommodityLiveInfoVO(limit, pages);
            return ResponseVO.getSuccessResponseVo(pageDataVO);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("查找实体店住的服务列表失败，请联系后台人员");
        }
    }

    @PostMapping("insertShopLive")
    @ApiOperation("发布实体店酒店的服务")
    public ResponseVO insertShopLive(@RequestBody ShopCommodityLiveInfo shopCommodityLiveInfo){
        try {
            boolean result = shopCommodityLiveInfoService.insertShopLive(shopCommodityLiveInfo);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("发布实体店酒店的服务成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("发布实体店酒店的服务失败，请联系后台人员");
        }
    }

    @PostMapping("updateShopLive")
    @ApiOperation("根据房型id更改房型信息")
    public ResponseVO updateShopLive(@RequestBody ShopCommodityLiveInfoUpVO shopCommodityLiveInfoUpVO){
        try {
            boolean result = shopCommodityLiveInfoService.updateShopLive(shopCommodityLiveInfoUpVO);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据房型id更改房型信息成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据房型id更改房型信息失败，请联系后台人员");
        }
    }

}
