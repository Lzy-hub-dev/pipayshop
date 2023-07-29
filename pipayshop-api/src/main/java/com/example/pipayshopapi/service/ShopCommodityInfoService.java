package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityVO;

import java.awt.*;

/**
 * <p>
 * 实体店的商品表 服务类
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
public interface ShopCommodityInfoService extends IService<ShopCommodityInfo> {

    /**
     * 发布实体店商品
     * @param shopCommodityVO
     * @param commodityImgList
     * @return
     */
    boolean issueShopCommodity(ShopCommodityVO shopCommodityVO, String commodityImgList);

    /**
     * 根据店铺id查找实体店商品的详情信息列表
     * @param shopId
     * @return
     */
    PageDataVO selectShopInfoListByShopId(Integer limit, Integer pages, String shopId);

    /**
     * 根据商品的id查找实体店商品的详情信息
     */
    ShopCommodityInfo selectShopInfoByCommodityId(String commodityId);



}
