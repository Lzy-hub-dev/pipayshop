package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.TransactionRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-08-08
 */
@Api(value = "交易记录",tags = "交易记录")
@RestController
@RequestMapping("/pipayshopapi/transaction-record")
public class TransactionRecordController {

    @Resource
    TransactionRecordService transactionRecordService;

    /**
     * 数据交换并记录交易，数据采用JWT 加密传输
     */
    @PostMapping("recordTransaction")
    @ApiOperation("数据交换并记录交易，数据采用JWT 加密传输")
    public ResponseVO<Boolean> recordTransaction( String token){
        try {
            boolean flag = transactionRecordService.recordTransaction(token);
            return new ResponseVO<>(200,"交易成功",flag);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("数据交换并记录交易失败，请联系后台人员");
        }
    }

    /**
     * 获取记账流水数据
     */
    @GetMapping("getRecordTransaction/{shopId}/{page}/{limit}")
    @ApiOperation("获取记账流水数据")
    public ResponseVO<PageDataVO> getRecordTransaction(@PathVariable String shopId,
                                                       @PathVariable int page,
                                                       @PathVariable int limit){
        try {
            PageDataVO list = transactionRecordService.     getRecordTransaction(shopId,page,limit);
            return ResponseVO.getSuccessResponseVo(list);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("获取记账流水数据失败，请联系后台人员");
        }
    }
}
