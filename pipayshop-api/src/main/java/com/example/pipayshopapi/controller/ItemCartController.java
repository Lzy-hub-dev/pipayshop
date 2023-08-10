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

    @GetMapping("selectItemCartByIds/{userId}")
    @ApiOperation("根据用户id展示购物车列表")
    public ResponseVO< List<ItemCartVO> > selectItemCartByIds(@PathVariable String userId){
        try {
            List<ItemCartVO>  pageDataVO = itemCartService.selectItemCartByIds(userId);
            return ResponseVO.getSuccessResponseVo(pageDataVO);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据用户id查找购物车失败，请联系后台人员");
        }
    }

    @PostMapping("putItemCartById/{userId}/{commodityId}/{sumCount}/{commoditySpec}")
    @ApiOperation("放进购物车")
    public ResponseVO putItemCartById(@PathVariable String userId,
                                      @PathVariable String commodityId,
                                      @PathVariable Integer sumCount,
                                      @PathVariable String commoditySpec){
        try {
            boolean result = itemCartService.putItemCartById(userId, commodityId,sumCount,commoditySpec);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("放进购物车成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("放进购物车失败，请联系后台人员");
        }
    }

    @PostMapping("outItemCartById/{cartId}/{commodityIds}")
    @ApiOperation("批量放出购物车")
    public ResponseVO outItemCartById(@PathVariable List<String> commodityIds,@PathVariable String cartId){
        try {
            boolean result = itemCartService.outItemCartById(commodityIds,cartId);
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
