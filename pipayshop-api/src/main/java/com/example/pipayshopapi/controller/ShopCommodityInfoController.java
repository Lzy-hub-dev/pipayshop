package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.ShopDetailInfoVO;
import com.example.pipayshopapi.entity.dto.ApplyShopCommodityDTO;
import com.example.pipayshopapi.entity.vo.ApplicationRecordVO;
import com.example.pipayshopapi.entity.vo.CommodityStatusPageVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ShopCommodityInfoService;
import com.example.pipayshopapi.util.FileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 实体店的商品表 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@Api(value = "实体店的商品表", tags = "实体店的商品表")
@RestController
@RequestMapping("/pipayshopapi/shop-commodity-info")
public class ShopCommodityInfoController {

    @Resource
    private ShopCommodityInfoService shopCommodityService;

    @GetMapping("getResidueByCommodityId/{CommodityId}")
    @ApiOperation("根据商品id获取商品库存")
    public ResponseVO getResidueByCommodityId(@PathVariable("CommodityId")String commodityId){
        try {
            Integer Residue = shopCommodityService.getResidueByCommodityId(commodityId);
            if (Residue == null) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(Residue);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("查询失败，请联系后台人员");
        }
    }


    @PostMapping(" issueShopCommodity")
    @ApiOperation("发布实体店商品")
    public ResponseVO<String> issueShopCommodity(ApplyShopCommodityDTO applyShopCommodityDTO) {

        try {
            boolean insert = shopCommodityService.issueShopCommodity(applyShopCommodityDTO);
            if (!insert) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("发布实体店商品成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("发布实体店商品失败，请联系后台人员");
        }
    }

    @GetMapping("selectShopInfoListByShopId/{pages}/{limit}/{shopId}")
    @ApiOperation("根据店铺id查找实体店商品的列表(商品帶标签)")
    public ResponseVO<PageDataVO> selectShopInfoListByShopId(@PathVariable Integer pages, @PathVariable Integer limit, @PathVariable String shopId) {
        try {
            PageDataVO pageDataVO = shopCommodityService.selectShopInfoListByShopId(limit, pages, shopId);
            return ResponseVO.getSuccessResponseVo(pageDataVO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("根据店铺id查找实体店商品的详情信息列表失败，请联系后台人员");
        }
    }

    @GetMapping("selectShopInfoByCommodityId/{commodityId}")
    @ApiOperation("根据商品的id查找实体店商品的  详情信息")
    public ResponseVO<ShopDetailInfoVO> selectShopInfoByCommodityId(@PathVariable String commodityId) {
        try {
            ShopDetailInfoVO shopDetailInfoVO = shopCommodityService.selectShopInfoByCommodityId(commodityId);
            return ResponseVO.getSuccessResponseVo(shopDetailInfoVO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("根据商品的id查找实体店商品的详情信息失败，请联系后台人员");
        }
    }

    @GetMapping("selectCommodityByShopIdAndStatus/{shopId}")
    @ApiOperation("根据店铺id查询商品列表(审核相关)")
    public ResponseVO<List<ApplicationRecordVO>> selectCommodityByUidAndStatus(@PathVariable("shopId")String shopId) {
        try {
            List<ApplicationRecordVO> list = shopCommodityService.selectCommodityByUidAndStatus(shopId);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("根据用户id查询，商品状态查询审核通过和未审核列表失败，请联系后台人员");
        }
    }

    @GetMapping("selectStatusListByShopId")
    @ApiOperation("根据店铺id查找实体店商品的上架和下架列表")
    public ResponseVO<PageDataVO> selectStatusListByShopId(CommodityStatusPageVO commodityStatusPageVO) {
        try {
            PageDataVO pageDataVO = shopCommodityService.selectStatusListByShopId(commodityStatusPageVO);
            return ResponseVO.getSuccessResponseVo(pageDataVO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("根据店铺id查找实体店商品的上架和下架列表失败，请联系后台人员");
        }
    }

    @PostMapping("updateCommodityStatus/{commodityId}/{status}")
    @ApiOperation("根据商品id，更改商品的上下架状态")
    public ResponseVO<String> updateCommodityStatus(@PathVariable String commodityId, @PathVariable Integer status) {
        try {
            boolean result = shopCommodityService.updateCommodityStatus(commodityId, status);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据商品id，更改商品的上下架状态成功");
        } catch (Exception e) {
            throw new BusinessException("根据商品id，更改商品的上下架状态失败，请联系后台人员");
        }
    }

    @PostMapping("updateCommodityUp/{commodityId}")
    @ApiOperation("根据商品id，上架变为下架")
    public ResponseVO<String> updateCommodityUp(@PathVariable String commodityId) {
        try {
            boolean result = shopCommodityService.updateCommodityUp(commodityId);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据商品id，上架变为下架成功");
        } catch (Exception e) {
            throw new BusinessException("根据商品id，上架变为下架失败，请联系后台人员");
        }
    }

    @PostMapping("updateCommodityCheck/{commodityId}")
    @ApiOperation("根据商品id，下架变为审核中")
    public ResponseVO<String> updateCommodityCheck(@PathVariable String commodityId) {
            boolean result = shopCommodityService.updateCommodityCheck(commodityId);
            if (!result) {
                throw new BusinessException("服务异常，请联系管理人员");
            }
            return ResponseVO.getSuccessResponseVo("根据商品id，下架变为审核中成功");

    }

    @PostMapping("shopCommodityTopImageUp")
    @ApiOperation("实体店商品展示图上传")
    public ResponseVO<String> shopCommodityTopImageUp(MultipartFile multipartFile){
        try {
            String shopCommodityTopImageUp = FileUploadUtil.uploadFile(multipartFile, FileUploadUtil.SHOP_COMMODITY_TOP_IMAGE_UP);
            return ResponseVO.getSuccessResponseVo(shopCommodityTopImageUp);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("实体店商品展示图上传失败，请联系后台人员");
        }
    }

    @PostMapping("shopCommodityImageUp")
    @ApiOperation("实体店商品轮播图上传")
    public ResponseVO<String> shopCommodityImageUp(MultipartFile multipartFile){
        try {
            String shopCommodityImageUp = FileUploadUtil.uploadFile(multipartFile, FileUploadUtil.SHOP_COMMODITY_IMAGE_UP);
            return ResponseVO.getSuccessResponseVo(shopCommodityImageUp);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("实体店商品轮播图上传失败，请联系后台人员");
        }
    }







}


