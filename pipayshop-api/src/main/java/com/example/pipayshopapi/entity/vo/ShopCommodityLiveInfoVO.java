package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.math.BigDecimal;

/**
 * fileName: ShopCommodityLiveInfoVO
 * author: 四面神
 * createTime:2023/8/4 12:10
 * 描述: 房型详情信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCommodityLiveInfoVO {

    /**
     * 房型id
     */
    private String roomId;

    /**
     * 房型名称
     */
    private String roomTypeName;

    /**
     * 出租土地平方米
     */
    private Integer land;

    /**
     * 限制
     */
    private String restricted;

    /**
     * 基础设施
     */
    private List<HotelFacilityVO> basics;

    /**
     * 洗沐设施
     */
    private List<HotelFacilityVO> bath;

    /**
     * 电器设施
     */
    private List<HotelFacilityVO> appliance;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 几间卫生间
     */
    private Integer restRoom;

    /**
     * 几个床
     */
    private Integer bed;

    /**
     * 能住几个成年人
     */
    private Integer adult;

    /**
     * 能住几个儿童
     */
    private Integer children;

    /**
     * 床型
     */
    private String bedType;

    /**
     * 楼层
     */
    private String floor;

    /**
     * 房间数
     */
    private Integer room;

    /**
     * 是否允许加客 0:可以 1：不可以
     */
    private Integer isAdd;

}
