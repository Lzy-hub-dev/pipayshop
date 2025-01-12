package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ItemCommodityHistory;
import com.example.pipayshopapi.entity.ShopCommodityHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.example.pipayshopapi.entity.ShopInfo;

import java.util.List;

/**
 * <p>
 * 实体店用户商品历史记录表 服务类
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
public interface ShopCommodityHistoryService extends IService<ShopCommodityHistory> {

    /**
     * 删除用户浏览商品的历史记录
     * @param userId
     * @param commodityId
     * @return
     */
    boolean deleteHistory(String userId, String commodityId);

    /**
     * 添加用户浏览商品的历史记录
     * @param shopCommodityHistory
     * @return
     */
    boolean addHistory(ShopCommodityHistory shopCommodityHistory);
}
