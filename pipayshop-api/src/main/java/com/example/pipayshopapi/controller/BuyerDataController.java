package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.BuyerData;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.BuyerDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 买家的收货地址（多选） 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "买家收货地址接口",tags = "买家收货地址接口")
@RestController
@RequestMapping("/pipayshopapi/buyer-data")
public class BuyerDataController {
    @Resource
    private BuyerDataService buyerDataService;

    @GetMapping("selectAllAddress/{userId}")
    @ApiOperation("根据用户Id查找用户的所有收货地址")
    public ResponseVO selectAllAddress(@PathVariable String userId){
        try {
            List<BuyerData> buyerData = buyerDataService.selectAllAddress(userId);
            return ResponseVO.getSuccessResponseVo(buyerData);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据用户Id查找用户的所有收货地址失败");
        }
    }


    @PostMapping("updateBuyerDataById")
    @ApiOperation("根据收货Id更改买家的收货地址")
    public ResponseVO updateBuyerDataById(@RequestBody BuyerData buyerData){
        try {
            boolean result = buyerDataService.updateBuyerDataById(buyerData);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据收货Id更改买家的收货地址成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据收货Id更改买家的收货地址失败，请联系后台人员");
        }
    }

    @PostMapping("insectBuyerData")
    @ApiOperation("插入买家的收货地址")
    public ResponseVO insectBuyerData(@RequestBody BuyerData buyerData){
        try {
            boolean result = buyerDataService.insectBuyerData(buyerData);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("插入买家的收货地址成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("插入买家的收货地址失败，请联系后台人员");
        }
    }


    @PostMapping("deleteBuyerDataById/{id}")
    @ApiOperation("根据id删除买家的收货地址等数据")
    public ResponseVO deleteBuyerDataById(@PathVariable String buyerId){
        try {
            boolean result = buyerDataService.deleteBuyerDataById(buyerId);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据id删除买家的收货地址");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据id删除买家的收货地址失败，请联系后台人员");
        }
    }

}
