package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ShopCommodityLive;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.InsertShopLiveVO;
import com.example.pipayshopapi.entity.vo.LivePageVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 实体店住的服务表 服务类
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
public interface ShopCommodityLiveService extends IService<ShopCommodityLive> {

    /**
     * 查找实体店住的服务列表
     */
    PageDataVO selectShopCommodityLiveVO(Integer limit, Integer pages);

    /**
     * 根据服务id查找服务的详情信息
     */
    ShopCommodityLive selectShopLiveById(String commodityId);

    /**
     * 条件筛选查找实体店住的服务列表
     */
    PageDataVO selectShopLiveVOByCondition(LivePageVO livePageVO);

    /**
     * 发布实体店住的服务
     */
    boolean insertShopLive(ShopCommodityLive shopCommodityLive);

    /**
     * 根据服务id更改服务信息
     */
    boolean updateShopLive(ShopCommodityLive shopCommodityLive);
}
