package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.BuyerData;
import com.example.pipayshopapi.entity.vo.BuyerDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.service.BuyerDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 买家的基本数据（多选） 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "买家基本数据接口",tags = "买家基本数据接口")
@RestController
@RequestMapping("/pipayshopapi/buyer-data")
public class BuyerDataController {
    @Resource
    private BuyerDataService buyerDataService;

    @GetMapping("selectBuyerDataById/{id}")
    @ApiOperation("根据Id查找买家的基本信息")
    public ResponseVO selectBuyerDataById(@PathVariable long id){
        try {
            System.out.println(id);
            BuyerDataVO buyerDataVO = buyerDataService.selectBuyerDataById(id);

            return ResponseVO.getSuccessResponseVo(buyerDataVO);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new RuntimeException("根据Id查找买家的基本信息失败，请联系后台人员");
        }
    }

    @PostMapping("updateBuyerDataById/{id}")
    @ApiOperation("根据Id更改买家的基本信息")
    public ResponseVO updateBuyerDataById(@RequestBody BuyerDataVO buyerDataVO,@PathVariable Long id){
        try {
            boolean result = buyerDataService.updateBuyerDataById(buyerDataVO,id);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据Id更改买家的基本信息成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据Id更改买家的基本信息失败，请联系后台人员");
        }
    }

    @PostMapping("insectBuyerData")
    @ApiOperation("插入买家的基本信息")
    public ResponseVO insectBuyerData(@RequestBody BuyerData buyerData){
        try {
            boolean result = buyerDataService.insectBuyerData(buyerData);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("插入买家的基本信息成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("插入买家的基本信息失败，请联系后台人员");
        }
    }


    @PostMapping("deleteBuyerDataById/{id}")
    @ApiOperation("根据Id删除买家的基本信息")
    public ResponseVO deleteBuyerDataById(@PathVariable long id){
        try {
            boolean result = buyerDataService.deleteBuyerDataById(id);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据Id删除买家的基本信息成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据Id删除买家的基本信息失败，请联系后台人员");
        }
    }

}
