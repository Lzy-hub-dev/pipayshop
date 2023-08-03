package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ShopCommodityLiveInfo;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.ShopCommodityLiveInfoMapper;
import com.example.pipayshopapi.service.ShopCommodityLiveInfoService;
import com.example.pipayshopapi.util.FileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

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
    public ResponseVO<ShopCommodityLiveInfo> selectShopLiveByRoomId(@PathVariable String roomId){
        try {
            ShopCommodityLiveInfo shopCommodityLiveInfo = shopCommodityLiveInfoService.selectShopLiveByRoomId(roomId);
            return ResponseVO.getSuccessResponseVo(shopCommodityLiveInfo);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据房型id查找房型的详细信息失败，请联系后台人员");
        }
    }


    @PostMapping("roomTopImageUp{multipartFile}")
    @ApiOperation("酒店头像图片上传")
    public ResponseVO<String> roomTopImageUp(@PathVariable MultipartFile multipartFile){
        try {
            String room_top_img = FileUploadUtil.uploadFile(multipartFile, FileUploadUtil.ROOM_TOP_IMG);
            return ResponseVO.getSuccessResponseVo(room_top_img);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("酒店头像图片上传失败，请联系后台人员");
        }
    }

    @PostMapping("roomImageUp/{multipartFile}")
    @ApiOperation("酒店轮播图上传")
    public ResponseVO<String> roomImageUp(@PathVariable MultipartFile multipartFile){
        try {
            String uploadFile = FileUploadUtil.uploadFile(multipartFile, FileUploadUtil.ROOM_IMAGE_LIST);
            return ResponseVO.getSuccessResponseVo(uploadFile);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("酒店轮播图上传失败，请联系后台人员");
        }
    }


    @PostMapping("insertShopLiveInfo")
    @ApiOperation("增加房型的详细信息")
    public ResponseVO<String> insertShopLiveInfo(@RequestBody ShopCommodityLiveInfo shopCommodityLiveInfo){
        try {
            boolean result = shopCommodityLiveInfoService.insertShopLiveInfo(shopCommodityLiveInfo);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("增加房型的详细信息成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("增加房型的详细信息失败，请联系后台人员");
        }
    }

    @PostMapping("updateShopLiveInfo")
    @ApiOperation("根据房型id更改房型的详细信息")
    // TODO 测
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

}
