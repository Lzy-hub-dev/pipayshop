package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.BgImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
