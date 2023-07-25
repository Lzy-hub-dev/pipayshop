package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.BgImg;
import com.example.pipayshopapi.entity.OrderInfo;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.BgImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 * item/shop的首页背景轮播图数据 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "店首页背景图片接口",tags = "店首页背景图片接口")
@RestController
@RequestMapping("/pipayshopapi/bg-img")
@Slf4j
public class BgImgController {


    @Resource
    private BgImgService bgImgService;


    @PostMapping("addBgImg")
    @ApiOperation("新增首页背景图片")
    public ResponseVO addBgImg(@RequestBody BgImg bgImg){

        try{
            boolean result= bgImgService.addBgImg(bgImg);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("新增首页背景图片成功");
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("新增首页背景图片失败，请联系后台人员");
        }
    }


    @PostMapping("logicDeleteBgImg/{bgId}")
    @ApiOperation("逻辑删除首页背景图片")
    public ResponseVO logicDeleteBgImg(@PathVariable String bgId){

        try{
            boolean result= bgImgService.logicDeleteBgImg(bgId);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("逻辑删除首页背景图片成功");
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("逻辑删除首页背景图片失败，请联系后台人员");
        }
    }

    @PostMapping("realDeleteBgImg/{bgId}")
    @ApiOperation("真实删除首页背景图片")
    public ResponseVO realDeleteBgImg(@PathVariable String bgId){

        try{
            boolean result= bgImgService.realDeleteBgImg(bgId);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("真实删除首页背景图片成功");
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("真实删除首页背景图片失败，请联系后台人员");
        }
    }


    @PostMapping("bgImgList/{bgId}")
    @ApiOperation("首页背景图片详情信息展示")
    public ResponseVO<BgImg> bgImgList(@PathVariable String bgId){

        try {
            BgImg bgImg = bgImgService.bgImgDetail(bgId);
            return ResponseVO.getSuccessResponseVo(bgImg);
        }catch(Exception e){
            log.error(e.getMessage());
            throw new BusinessException("首页背景图片详情信息展示失败，请联系后台相关人员");
        }
    }


    @PostMapping("editBgImg")
    @ApiOperation("修改店的首页背景图片信息")
    public ResponseVO editBgImg(@RequestBody BgImg bgImg){

        try{
            boolean result = bgImgService.editBgImg(bgImg);
            if (!result)
                throw new Exception();
            return ResponseVO.getSuccessResponseVo("修改店的首页背景图片信息成功");
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("修改店的首页背景图片信息失败，请联系后台相关人员");
        }
    }










}
