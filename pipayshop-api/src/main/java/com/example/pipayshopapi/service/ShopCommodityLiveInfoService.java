package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ShopCommodityLiveInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.dto.ShopHotelRecordDTO;
import com.example.pipayshopapi.entity.vo.HotelFacilityVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityLiveInfoListVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityLiveInfoVO;

import java.util.Date;
import java.util.List;

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
    ShopCommodityLiveInfoVO selectShopLiveByRoomId(String roomId);

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

    /**
     * 根据实体店id和入住时间和离店时间来搜索房型
     */
    List<ShopCommodityLiveInfoListVO> selectShopCommodityLiveInfoList(String shopId, Date startTime,Date endTime);

    /**
     * 获取实体店酒店的评价数
     */
    Integer selectShopEvaluateCount(String shopId);

    /**
     * 提交入住酒店
     */
    boolean applyShopCommodityLive(ShopHotelRecordDTO shopHotelRecordDTO);
}
