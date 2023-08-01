package com.example.pipayshopapi.controller;

import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ItemCommodityEvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@RestController
@RequestMapping("/pipayshopapi/item-commodity-evaluate")
@Api(value = "网店商品评价表",tags = "网店商品评价表")
@Slf4j
public class ItemCommodityEvaluateController {

    @Resource
    private ItemCommodityEvaluateService itemCommodityEvaluateService;


    @GetMapping("getItemCommodityEvaluates/{commodityId}/{currentPage}/{limit}")
    @ApiOperation("获取网店商品评价列表")
    public ResponseVO getItemCommodityEvaluates(@PathVariable String commodityId,@PathVariable Integer currentPage,@PathVariable Integer limit){

        try{
            PageDataVO itemCommodityEvaluates = itemCommodityEvaluateService.getItemCommodityEvaluates(commodityId,currentPage,limit);
            return ResponseVO.getSuccessResponseVo(itemCommodityEvaluates);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("获取网店商品评价列表失败，请联系后台人员");
        }
    }

}
