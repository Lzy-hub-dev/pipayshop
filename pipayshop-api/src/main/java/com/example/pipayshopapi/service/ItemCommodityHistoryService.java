package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ItemCommodityHistory;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
