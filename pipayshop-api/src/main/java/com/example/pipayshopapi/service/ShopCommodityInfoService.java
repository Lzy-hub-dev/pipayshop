package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.vo.ShopCommodityVO;

import java.util.List;

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
     * 根据用户id查询 用户收藏的商品列表
     * @param userId
     * @return
     */
    List<ShopCommodityInfo> getCollectList(String userId);
    /**
     * 根据用户id查询用户关注的网店列表
     * @param userId
     * @return
     */
    List<ShopInfo> getFollowList(String userId);

    /**
     * 根据用户id查询用户浏览商品历史-实体店
     * @param userId
     * @return
     */
    List<ShopCommodityVO> historyList(String userId);
}
