package com.example.pipayshopapi.controller;

import com.example.pipayshopapi.entity.ShopTags;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.service.ShopTagsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 实体店的标签 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "实体店标签",tags = "实体店标签")
@RestController
@RequestMapping("/pipayshopapi/shop-tags")
public class ShopTagsController {
    @Autowired
    private ShopTagsService tagsService;

    private static final Logger log = LoggerFactory.getLogger(ShopTagsController.class);

    @GetMapping("getShopTagsList")
    @ApiOperation("查询标签列表")
    public ResponseVO getShopTagsList(Integer pageNum, Integer pageSize) {
        try {
            PageDataVO list = tagsService.getShopTagsList(pageNum, pageSize);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }
    @GetMapping("getShopTagsById/{shopId}")
    @ApiOperation("根据id查询标签详情")
    public ResponseVO getShopTagsById(@PathVariable String shopId) {
        try {
            ShopTags shopById = tagsService.getShopTagsById(shopId);
            return ResponseVO.getSuccessResponseVo(shopById);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }
    @PostMapping("deleteShopTagsById/{shopId}")
    @ApiOperation("根据id删除标签")
    public ResponseVO deleteShopTagsById(@PathVariable String shopId) {
        try {
            Boolean aBoolean = tagsService.deleteShopTagsById(shopId);
            return aBoolean ? ResponseVO.getSuccessResponseVo(null) : ResponseVO.getFalseResponseVo(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }
    @PostMapping("updateShopTagsById")
    @ApiOperation("根据id修改标签")
    public ResponseVO updateShopTagsById(@RequestBody ShopTags shopTags) {
        try {
            Boolean aBoolean = tagsService.updateShopTagsById(shopTags);
            return aBoolean ? ResponseVO.getSuccessResponseVo(null) : ResponseVO.getFalseResponseVo(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }
    @PostMapping("addShopTags")
    @ApiOperation("新增标签")
    public ResponseVO addShopTags(@RequestBody ShopTags shopTags) {
        try {
            Boolean aBoolean = tagsService.addShopTags(shopTags);
            return aBoolean ? ResponseVO.getSuccessResponseVo(null) : ResponseVO.getFalseResponseVo(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }
}
