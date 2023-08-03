package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.dto.ApplyShopCommodityDTO;
import com.example.pipayshopapi.entity.vo.CommodityStatusPageVO;
import com.example.pipayshopapi.entity.vo.OrderPageVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityVO;
import org.springframework.web.multipart.MultipartFile;

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
     */
    boolean issueShopCommodity(ApplyShopCommodityDTO applyShopCommodityDTO, MultipartFile[] files);
    /**
     * 根据用户id查询 用户收藏的商品列表
     */
    List<ShopCommodityInfo> getCollectList(String userId);
    /**
     * 根据用户id查询用户关注的网店列表
     */
    List<ShopInfo> getFollowList(String userId);

    /**
     * 根据店铺id查找实体店商品的详情信息列表
     */
    PageDataVO selectShopInfoListByShopId(Integer limit, Integer pages, String shopId);

    /**
     * 根据店铺id查找实体店商品的上架和下架列表
     */
    PageDataVO selectStatusListByShopId(CommodityStatusPageVO commodityStatusPageVO);

    /**
     * 根据商品的id查找实体店商品的详情信息
     */
    ShopCommodityInfo selectShopInfoByCommodityId(String commodityId);



    /**
     * 根据用户id查询用户浏览商品历史-实体店
     */
    List<ShopCommodityVO> historyList(String userId);

    /**
     * 根据用户id查询，商品状态查询审核通过和未审核列表
     */
    PageDataVO selectCommodityByUidAndStatus(OrderPageVO pageVO);

    /**
     * 根据商品id，更改商品的状态
     */
    boolean updateCommodityStatus(String commodityId,Integer status);
}
