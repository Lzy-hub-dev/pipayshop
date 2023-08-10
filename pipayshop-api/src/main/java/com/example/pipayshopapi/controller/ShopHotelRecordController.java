package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ShopHotelRecord;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopHotelRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 记录酒店入住信息表 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-08-02
 */
@Api(value = "记录酒店入住信息接口",tags = "记录酒店入住信息接口")
@RestController
@RequestMapping("/pipayshopapi/shop-hotel-record")
public class ShopHotelRecordController {
    @Resource
    private ShopHotelRecordService shopHotelRecordService;

    @PostMapping("createShopHotelRecord")
    @ApiOperation("记录酒店入住信息接口")
    private ResponseVO createShopHotelRecord(ShopHotelRecord shopHotelRecord){
        try {
            Boolean result = shopHotelRecordService.createShopHotelRecord(shopHotelRecord);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("记录酒店入住信息接口成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("记录酒店入住信息接口失败，请联系后台人员");
        }
    }

}
