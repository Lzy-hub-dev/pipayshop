package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.dto.ApplyItemCommodityDTO;
import com.example.pipayshopapi.entity.dto.ItemSearchConditionDTO;
import com.example.pipayshopapi.entity.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 网店的商品表 服务类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
public interface ItemCommodityInfoService extends IService<ItemCommodityInfo> {


    /**
     *某二级分类下的商品列表分页展示
     */
    PageDataVO commodityOfCateList(commodityPageVO commodityPageVO);

    /**
     * 发布网店商品
     * @param applyItemCommodityDTO
     * @param file
     * @return
     */
    boolean issueItemCommodity(ApplyItemCommodityDTO applyItemCommodityDTO, MultipartFile[] file);

    /**
     *首页下面的商品列表分页展示
     */
    PageDataVO itemSearchCommodity(ItemSearchConditionDTO itemSearchConditionDTO);


    /**
     *商品展示choose展示
     */
    List<commodityVO> itemCommodityChoose(String itemId,String brandId);

    /**
     *商品详情展示
     */
    CommodityDetailVO itemCommodityDetail(String commodityId);

    /**
     * 根据用户id查询 对应的 网店收藏列表
     * @param userId
     * @return
     */
    List<ItemCommodityInfo> getCollectList(String userId);

    /**
     * 根据用户id查询 对应的 网店关注列表
     * @param userId
     * @return
     */
    List<ItemInfo> getFollowList(String userId);
    /**
     * 根据用户id查询用户浏览商品历史-网店
     * @param userId
     * @return
     */
    List<ShopCommodityVO> historyList(String userId);
}
