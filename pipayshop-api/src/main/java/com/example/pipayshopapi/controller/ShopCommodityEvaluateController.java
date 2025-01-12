package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.dto.ShopCommodityEvaluateDTO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopCommodityEvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@RestController
@Api(value = "实体店商品评价表", tags = "实体店商品评价表")
@RequestMapping("/pipayshopapi/shop-commodity-evaluate")
public class ShopCommodityEvaluateController {

    @Resource
    private ShopCommodityEvaluateService evaluationService;

    @GetMapping("commodityEvaluateList/{commodityId}/{pageNum}/{pageSize}")
    @ApiOperation("实体店-商品-评论列表")
    public ResponseVO<PageDataVO> commodityEvaluateList(@PathVariable String commodityId,
                                                        @ApiParam("当前页")
                                                        @PathVariable Integer pageNum,
                                                        @ApiParam("展示条数")
                                                        @PathVariable Integer pageSize) {
        try {
            PageDataVO result = evaluationService.commodityEvaluateList(commodityId, pageNum, pageSize);
            return ResponseVO.getSuccessResponseVo(result);
        } catch (Exception e) {
            throw new BusinessException("查看评论列表失败请联系后台人员");
        }
    }

    @PostMapping("addEvaluate")
    @ApiOperation("实体店-商品-添加评论")
    public ResponseVO<String> addEvaluate(ShopCommodityEvaluateDTO dto) {
        try {
            Boolean result = evaluationService.addEvaluate(dto);
            if (!result) {
                return ResponseVO.getFalseResponseVo("新增评论失败");
            }
            return ResponseVO.getSuccessResponseVo("新增评论成功");
        } catch (Exception e) {
            throw new BusinessException("新增评论失败,请联系后台人员");
        }
    }

    @PostMapping("deleteEvaluate/{evaluateId}/{userId}")
    @ApiOperation("实体店-商品-删除评论")
    public ResponseVO<String> deleteEvaluate(@PathVariable String evaluateId, @PathVariable String userId) {
        try {
            Boolean result = evaluationService.deleteEvaluate(evaluateId, userId);
            if (!result) {
                return ResponseVO.getFalseResponseVo("删除评论失败");
            }
            return ResponseVO.getSuccessResponseVo("删除成功");
        } catch (Exception e) {
            throw new BusinessException("删除评论失败请联系后台人员");
        }
    }
}
