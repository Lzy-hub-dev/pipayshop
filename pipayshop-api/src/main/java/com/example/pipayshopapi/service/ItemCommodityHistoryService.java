package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ItemCommodityHistory;

/**
 * <p>
 * 网店用户商品历史记录表 服务类
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
public interface ItemCommodityHistoryService extends IService<ItemCommodityHistory> {


    /**
     * 删除用户浏览网店商品的历史记录
     * @param userId
     * @param commodityId
     * @return
     */
    boolean deleteHistory(String userId, String commodityId);

    /**
     * 新增用户浏览网店商品的历史记录
     * @param itemCommodityHistory
     * @return
     */
    boolean addHistory(ItemCommodityHistory itemCommodityHistory);

    /**
     * 定期轮询删除超过十天的浏览历史足迹记录
     */
    boolean orderDeleteHistory();
}
