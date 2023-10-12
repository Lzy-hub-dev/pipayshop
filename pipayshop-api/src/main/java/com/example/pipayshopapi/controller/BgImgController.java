package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.BgImg;
import com.example.pipayshopapi.entity.dto.BgImgDTO;
import com.example.pipayshopapi.entity.vo.BgImgVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.BgImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

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
    @GetMapping("selectBgImgList/{category}")
    @ApiOperation("查询网店 / 实体店首页轮播背景图列表")
    public ResponseVO<List<BgImgVO>> selectBgImgList(@PathVariable int category){
        try{
            List<BgImgVO> voList = bgImgService.selectBgImgList(category);
            return ResponseVO.getSuccessResponseVo(voList);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("查询首页轮播背景图片失败，请联系后台人员");
        }
    }
    @PostMapping("addBgImg")
    @ApiOperation("新增首页背景轮播图")
    //Todo 后面要补全只对哪个服务器开放
    @CrossOrigin
    public ResponseVO addBgImg(BgImgDTO bgImgDTO){
        try{
            Boolean result= bgImgService.addBgImg(bgImgDTO);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("新增首页背景轮播图成功");
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("新增首页背景轮播图片失败，请联系后台人员");
        }
    }

    /**
     * 网店首页的分类栏上传图片
     */
    @PostMapping("bgCategoryImage")
    @ApiOperation("网店、实体店首页的轮播图上传图片")
    //Todo 后面要补全只对哪个服务器开放
    @CrossOrigin
    public ResponseVO<String> bgCategoryImage(@RequestParam("file") MultipartFile multipartFile){
        try {
            String imageId = bgImgService.bgCategoryImage(multipartFile);
            return ResponseVO.getSuccessResponseVo(imageId);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("网店、实体店首页的轮播图上传图片失败，请联系后台人员");
        }
    }
}
