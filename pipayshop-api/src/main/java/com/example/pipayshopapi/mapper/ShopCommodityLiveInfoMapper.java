package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ShopCommodityLiveInfo;
import com.example.pipayshopapi.entity.vo.ShopCommodityLiveInfoListVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityLiveInfoVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityLiveVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 酒店的房型表 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-08-02
 */
@Mapper
public interface ShopCommodityLiveInfoMapper extends BaseMapper<ShopCommodityLiveInfo> {

    List<ShopCommodityLiveInfoListVO> selectShopCommodityLiveInfoList(@Param("shopId")String shopId);

    ShopCommodityLiveInfoVO selectByRoomId(@Param("roomId")String roomId);

    @Select("select basics from shop_commodity_live_info where room_id = #{roomId}")
    String getBasic(@Param("roomId")String roomId);

    @Select("select bath from shop_commodity_live_info where room_id = #{roomId}")
    String getBath(@Param("roomId")String roomId);

    @Select("select appliance from shop_commodity_live_info where room_id = #{roomId}")
    String getAppliance(@Param("roomId")String roomId);

    List<ShopCommodityLiveVO> selectShopCommodityLiveVO(@Param("limit")Integer limit, @Param("pages")Integer pages);

    Integer selectAllShopCommodityLiveVO();
}
