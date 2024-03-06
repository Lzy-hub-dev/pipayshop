package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ShopCommodityLiveInfo;
import com.example.pipayshopapi.entity.dto.ShopCommodityLiveInfoListDTO1;
import com.example.pipayshopapi.entity.vo.*;
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

    int reduceLiveStock(@Param("num")Integer num,@Param("commodityId")String commodityId);

    /**
     * 根据购买数量、商品id =》商品库存复原
     * @return
     */
    int addLiveStock(@Param("num")Integer num,@Param("commodityId")String commodityId);

    /**
     * 查出酒店下面所有的房间，条件：价格（非必要）
     * @param hotelInfo
     * @return
     */
    List<ShopCommodityLiveInfoVO3> selectALlByPrice(ShopCommodityLiveInfoListDTO1 hotelInfo);
}
