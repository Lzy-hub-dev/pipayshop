package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ShopCommodityLive;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.ItemCartVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityLiveVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 实体店住的服务表 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@Mapper
public interface ShopCommodityLiveMapper extends BaseMapper<ShopCommodityLive> {

    List<ShopCommodityLiveVO> selectShopCommodityLiveVO(@Param("limit")Integer limit, @Param("pages")Integer pages);

    Integer selectAllShopCommodityLiveVO();

    List<ShopCommodityLiveVO> selectShopLiveVOByCondition(@Param("limit")Integer limit,@Param("pages") Integer pages,@Param("checkInTime") Date checkInTime,
                                                          @Param("departureTime")Date departureTime,@Param("adult")Integer adult,@Param("children")Integer children);

    Integer selectAllShopLiveVOByCondition(@Param("checkInTime") Date checkInTime,
                                                          @Param("departureTime")Date departureTime,@Param("adult")Integer adult,@Param("children")Integer children);


}
