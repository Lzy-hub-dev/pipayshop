package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopCommodityLiveInfo;
import com.example.pipayshopapi.entity.dto.RoomDto;
import com.example.pipayshopapi.entity.dto.ShopCommodityLiveInfoListDTO1;
import com.example.pipayshopapi.entity.dto.ShopHotelRecordDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

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
    boolean insertShopLiveInfo(ShopCommodityLiveInfoVO1 shopCommodityLiveInfo);

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

    PageDataVO selectShopCommodityLiveInfoVO(Integer limit, Integer pages);

    boolean insertShopLive(ShopCommodityLiveInfo shopCommodityLiveInfo);

    boolean updateShopLive(ShopCommodityLiveInfoUpVO shopCommodityLiveInfoUpVO);

    String roomTopImageUp(MultipartFile multipartFile);

    String roomImageUp(MultipartFile multipartFile);

    List<ShopCommodityLiveInfoVO2> selectAllByPrice(ShopCommodityLiveInfoListDTO1 ahopInfo) throws JsonProcessingException;

    int updateRoomStatus(RoomDto room);
}
