package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.ShopCommodityVO;

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

}
