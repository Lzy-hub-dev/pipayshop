package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ItemCollection;

/**
 * <p>
 * 网店商品收藏表	 服务类
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
public interface ItemCollectionService extends IService<ItemCollection> {

    int AddItemCommodityToCollection(String userId, String commodityId);

    int closeItemCommodityToCollection(String userId, String commodityId);

    boolean isItemCommodityToCollection(String userId, String commodityId);
}
