package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ItemCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  网店购物车 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-07-28
 */
@Api(value = "网店购物车接口",tags = "网店购物车接口")
@RestController
@RequestMapping("/pipayshopapi/item-cart")
public class ItemCartController {
    @Resource
    private ItemCartService itemCartService;

    // TODO 错
    @GetMapping("selectItemCartByIds/{currentPage}/{limit}/{userId}")
    @ApiOperation("根据用户id展示购物车列表")
    public ResponseVO selectItemCartByIds(@PathVariable Integer currentPage, @PathVariable Integer limit,@PathVariable String userId){
        try {
            PageDataVO pageDataVO = itemCartService.selectItemCartByIds(limit, currentPage, userId);
            return ResponseVO.getSuccessResponseVo(pageDataVO.getList());
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据用户id查找购物车失败，请联系后台人员");
        }
    }

    @PostMapping("putItemCartById/{userId}/{commodityId}")
    @ApiOperation("放进购物车")
    public ResponseVO putItemCartById(@PathVariable String userId,@PathVariable String commodityId){
        try {
            boolean result = itemCartService.putItemCartById(userId, commodityId);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("放进购物车成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("放进购物车失败，请联系后台人员");
        }
    }

    @PostMapping("outItemCartById/{cartIds}")
    @ApiOperation("批量放出购物车")
    public ResponseVO outItemCartById(@PathVariable List<String> cartIds){
        try {
            boolean result = itemCartService.outItemCartById(cartIds);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("批量放出购物车成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("批量放出购物车失败，请联系后台人员");
        }
    }
}
