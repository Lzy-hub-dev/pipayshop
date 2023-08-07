package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.dto.ApplyItemCommodityDTO;
import com.example.pipayshopapi.entity.dto.ItemSearchConditionDTO;
import com.example.pipayshopapi.entity.vo.*;

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
    PageDataVO commodityOfCateList(CommodityPageVO commodityPageVO);

    /**
     * 发布网店商品
     * @param applyItemCommodityDTO
     * @return
     */
    boolean issueItemCommodity(ApplyItemCommodityDTO applyItemCommodityDTO);


    /**
     *首页下面的商品列表分页展示
     */
    PageDataVO itemSearchCommodity(ItemSearchConditionDTO itemSearchConditionDTO);


    /**
     *商品展示choose展示
     */
    List<CommodityVO> itemCommodityChoose(String itemId, String brandId);

    /**
     *商品详情展示
     */
    CommodityDetailVO itemCommodityDetail(String commodityId);

    /**
     * 根据用户id查询 对应的 网店收藏列表
     * @param userId
     * @return
     */
    PageDataVO getCollectList(Integer page,Integer limit,String userId);


    /**
     * 根据用户id查询用户浏览商品历史-网店
     * @param userId
     * @return
     */
    PageDataVO historyList(Integer page,Integer limit,String userId);

    /**
     * 根据卖家id查询网店的商品审核列表
     */
    List<AuditItemVO> examineCommodityList(String itemId);
    /**
     * 根据网店id查询网店的商品列表
     */
    ItemInfoVO commodityList(String itemId);

    /**
     *
     * @param commodity
     * @param status 1:上架;2:下架
     * @return
     */
    boolean changeCommodityStatus(String commodity, String status);

    /**
     * 根据商品id，上架变为下架
     *
     * @param commodity
     * @return
     */
    boolean changeCommodityUp(String commodity);
    /**
     * 根据商品id，下架变为审核中
     *
     * @param commodity
     * @return
     */
    boolean changeCommodityCheck(String commodity);


}
