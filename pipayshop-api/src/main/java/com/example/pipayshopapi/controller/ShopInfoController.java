package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.dto.ApplyShopDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopInfoService;
import com.example.pipayshopapi.util.FileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 实体店的信息 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "实体店的信息接口",tags = "实体店的信息接口")
@RestController
@RequestMapping("/pipayshopapi/shop-info")
public class ShopInfoController {
    @Autowired
    private ShopInfoService infoService;
    private static final Logger log = LoggerFactory.getLogger(ShopTagsController.class);


    @GetMapping("getShopInfoListByCondition/{limit}/{pages}/{categoryId}/{score}")
    @ApiOperation("根据条件获取所有实体店列表")
    public ResponseVO<PageDataVO> getShopInfoListByCondition(@PathVariable Integer limit,@PathVariable Integer pages,@PathVariable String categoryId,@PathVariable Boolean score){
        try {
            PageDataVO shopInfoListByCondition = infoService.getShopInfoListByCondition(limit, pages, categoryId,score);
            if (shopInfoListByCondition==null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(shopInfoListByCondition);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据二级分类-获取所有实体店列表失败，请联系后台人员");
        }
    }

    @GetMapping("getShopInfoVO/{shopId}")
    @ApiOperation("根据实体店id查询实体店信息(我的)")
    public ResponseVO getShopInfoVO(@PathVariable String shopId){
        try {
            ShopInfoVO1 shopInfoVO = infoService.getShopInfoVO(shopId);
            return ResponseVO.getSuccessResponseVo(shopInfoVO);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据实体店id查询实体店信息失败，请联系后台人员");
        }
    }

    @GetMapping("getShopList")
    @ApiOperation("根据用户id查询实体店列表(我的)")
    public ResponseVO getShopList(UidPageVO uidPageVO){
        try {
            PageDataVO shopList = infoService.getShopList(uidPageVO);
            return ResponseVO.getSuccessResponseVo(shopList);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据用户id查询实体店列表失败，请联系后台人员");
        }
    }

    @GetMapping("getShopNumber/{uid}")
    @ApiOperation("根据用户id查询用户名下多少间实体店")
    public ResponseVO getShopNumber(@PathVariable String uid){
        try {
            Integer shopNumber = infoService.getShopNumber(uid);
            return ResponseVO.getSuccessResponseVo(shopNumber);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据用户id查询用户名下多少间实体店失败，请联系后台人员");
        }
    }

    @GetMapping("getShopInfoById/{shopId}")
    @ApiOperation("根据id查询实体店详情")
    public ResponseVO getShopInfoById(@PathVariable String shopId) {
        try {
            ShopInfoVO shopByIdVO = infoService.getShopInfoById(shopId);
            return ResponseVO.getSuccessResponseVo(shopByIdVO);
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
    public ResponseVO applyShop(ApplyShopDTO applyShopDTO) {
        try {
            Boolean insert = infoService.applyShop(applyShopDTO);
            if (!insert){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("提交申请成功");
         } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo("提交申请失败，请联系后台管理员");
        }
    }


    /**
     * 根据店铺id查询该店铺是否为vip店铺
     */
    @GetMapping("isVipShop/{shopId}")
    @ApiOperation("根据店铺id查询该店铺是否为vip店铺")
    public ResponseVO<Boolean> isVipShop(@PathVariable("shopId") String shopId) {
        try {
            boolean flag = infoService.isVipShop(shopId);
            return ResponseVO.getSuccessResponseVo(flag);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("根据店铺id查询该店铺是否为vip店铺失败，请联系后台人员");
        }
    }

    /**
     *  根据用户id查找名下的所有实体店铺的shopId列表
     */
    @GetMapping("getShopIdListByUid/{uid}")
    @ApiOperation("根据用户id查找名下的所有实体店铺的shopId列表")
    public ResponseVO<List<String>> getShopIdListByUid(@PathVariable("uid") String uid) {
        try {
            List<String> list = infoService.getShopIdListByUid(uid);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("根据用户id查找名下的所有实体店铺的shopId列表失败，请联系后台人员");
        }
    }

    /**
     * 将多家实体店一起升级为vip店铺
     */
    @PostMapping("upVipByShopIdList")
    @ApiOperation("将多家实体店一起升级为vip店铺")
    public ResponseVO<String> upVipByShopIdList(String shopIds) {
        try {
            Boolean update = infoService.upVipByShopIdList(shopIds);
            if (!update){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("将多家实体店一起升级为vip店铺");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.getFalseResponseVo("将多家实体店一起升级为vip店铺失败，请联系后台管理员");
        }
    }

    @GetMapping("updateShopCommodity/{shopId}")
    @ApiOperation("查询指定实体店还可以上传的商品数量")
    public ResponseVO<Integer> updateShopCommodity(@PathVariable String shopId){
        try {
            Integer integer = infoService.updateShopCommodity(shopId);
            return ResponseVO.getSuccessResponseVo(integer);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("失败");
        }
    }

    @PostMapping("shopTopImageUp")
    @ApiOperation("实体店展示图上传")
    public ResponseVO<String> shopTopImageUp(MultipartFile multipartFile){
        try {
            String shopTopImageUp = FileUploadUtil.uploadFile(multipartFile, FileUploadUtil.SHOP_TOP_IMAGE_UP);
            return ResponseVO.getSuccessResponseVo(shopTopImageUp);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("实体店展示图上传失败，请联系后台人员");
        }
    }

    @PostMapping("shopImageUp")
    @ApiOperation("实体店轮播图上传")
    public ResponseVO<String> shopImageUp(MultipartFile multipartFile){
        try {
            String shopImageUp = FileUploadUtil.uploadFile(multipartFile, FileUploadUtil.SHOP_IMAGE_UP);
            return ResponseVO.getSuccessResponseVo(shopImageUp);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("实体店轮播图上传失败，请联系后台人员");
        }
    }

    /**
     * 校验商家的付款ID
     */
    @GetMapping("checkId/{qrcode}")
    @ApiOperation("校验商家的付款ID")
    public ResponseVO<CheckVO> checkId(@PathVariable String qrcode){
        try {
            CheckVO checkId = infoService.checkId(qrcode);
            return ResponseVO.getSuccessResponseVo(checkId);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("失败");
        }
    }

    /**
     *
     */
    @PostMapping("piIdImageUp")
    @ApiOperation("pi_Id_Image上传")
    public ResponseVO<String> piIdImageUp(MultipartFile multipartFile){
        try {
            String piIdImageUp = FileUploadUtil.uploadFile(multipartFile, FileUploadUtil.PI_ID_IMAGE);
            return ResponseVO.getSuccessResponseVo(piIdImageUp);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("pi_Id_Image上传失败，请联系后台人员");
        }
    }

}
