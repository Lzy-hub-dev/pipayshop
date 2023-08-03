package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ShopCommodityLiveInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 酒店的房型表 服务类
 * </p>
 *
 * @author zxb
 * @since 2023-08-02
 */
public interface ShopCommodityLiveInfoService extends IService<ShopCommodityLiveInfo> {

    /**
     * 根据房型id查找房型的详细信息
     */
    public ShopCommodityLiveInfo selectShopLiveByRoomId(String roomId);

    /**
     * 增加房型的详细信息
     */
    boolean insertShopLiveInfo(ShopCommodityLiveInfo shopCommodityLiveInfo);

    /**
     * 根据房型id更改房型的详细信息
     */
    boolean updateShopLiveInfo(ShopCommodityLiveInfo shopCommodityLiveInfo);

    /**
     * 根据房型id删除房型的详细信息
     */
    boolean deleteShopLiveInfo(String roomId);

}
