package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.dto.ApplyShopDTO;
import com.example.pipayshopapi.entity.dto.ShopDTO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 实体店的信息 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */

@RestController
@RequestMapping("/pipayshopapi/shop-info")
public class ShopInfoController {
    @Autowired
    private ShopInfoService infoService;
    private static final Logger log = LoggerFactory.getLogger(ShopTagsController.class);

    @GetMapping("getShopInfoList")
    @ApiOperation("根据条件查询实体店列表")
    public ResponseVO getShopInfoList(ShopDTO shopDTO) {
        try {
            PageDataVO list = infoService.getShopInfoList(shopDTO);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }

    @GetMapping("getShopInfoListByCondition/{limit}/{pages}/{categoryId}/{state}")
    @ApiOperation("根据条件筛选后获取实体店列表")
    public ResponseVO getShopInfoListByCondition(@PathVariable Integer limit,@PathVariable Integer pages,@PathVariable String categoryId,@PathVariable Integer state){
        try {
            PageDataVO list = infoService.getShopInfoListByCondition(limit,pages,categoryId,state);
            if (list==null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(list.getList());
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据条件筛选后获取实体店列表失败，请联系后台人员");
        }
    }

    @GetMapping("getShopInfoById/{shopId}")
    @ApiOperation("根据id查询实体店详情")
    public ResponseVO getShopInfoById(@PathVariable String shopId) {
        try {
            ShopInfo shopById = infoService.getShopInfoById(shopId);
            return ResponseVO.getSuccessResponseVo(shopById);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }
    @PostMapping("deleteShopInfoById/{shopId}")
    @ApiOperation("根据id删除实体店")
    public ResponseVO deleteShopInfoById(@PathVariable String shopId) {
        try {
            Boolean aBoolean = infoService.deleteShopInfoById(shopId);
            return aBoolean ? ResponseVO.getSuccessResponseVo(null) : ResponseVO.getFalseResponseVo(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }
    }
    @PostMapping("updateShopInfoById")
    @ApiOperation("根据id修改实体店")
    public ResponseVO updateShopInfoById(@RequestBody ShopInfo shopInfo) {
        try {
            Boolean aBoolean = infoService.updateShopInfoById(shopInfo);
            return aBoolean ? ResponseVO.getSuccessResponseVo(null) : ResponseVO.getFalseResponseVo(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo(null);
        }

    }
    @PostMapping("applyShop")
    @ApiOperation("申请实体店")
    public ResponseVO applyShop(ApplyShopDTO applyShopDTO,@RequestParam("files") MultipartFile[] files) {
        try {
            Boolean insert = infoService.applyShop(applyShopDTO,files);
            if (!insert){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("提交申请成功");
         } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo("提交申请失败，请联系后台管理员");
        }
    }


}
