package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ShopCollection;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 实体店服务收藏表		 服务类
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
public interface ShopCollectionService extends IService<ShopCollection> {

    int addShopCollection(String userId, String commodityId);

    int removeShopCollection(String userId, String commodityId);

    boolean isCommodityToCollection(String userId, String commodityId);
}
