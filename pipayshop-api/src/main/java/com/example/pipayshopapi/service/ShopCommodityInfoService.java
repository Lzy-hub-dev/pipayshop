package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.dto.ApplyShopCommodityDTO;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.dto.ApplyShopCommodityDTO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityVO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     * @param applyShopCommodityDTO
     * @param files
     * @return
     */
    boolean issueShopCommodity(ApplyShopCommodityDTO applyShopCommodityDTO, MultipartFile[] files);
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
     * 根据店铺id查找实体店商品的详情信息列表
     * @param shopId
     * @return
     */
    PageDataVO selectShopInfoListByShopId(Integer limit, Integer pages, String shopId);

    /**
     * 根据商品的id查找实体店商品的详情信息
     */
    ShopCommodityInfo selectShopInfoByCommodityId(String commodityId);



    /**
     * 根据用户id查询用户浏览商品历史-实体店
     * @param userId
     * @return
     */
    List<ShopCommodityVO> historyList(String userId);
}
