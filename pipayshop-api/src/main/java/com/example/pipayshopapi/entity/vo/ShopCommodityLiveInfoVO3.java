package com.example.pipayshopapi.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCommodityLiveInfoVO3 {
    /**
     * 所属酒店id的经纬度、联系电话、地址
     */
    private BigDecimal latitude;

    private BigDecimal longitude;

    private String phone;

    private String address;

    /**
     * 房型名称
     */

    /**
     * 房型id
     */
    private String roomId;
    /**
     * 房屋类型名称
     */
    private String roomTypeName;

    /**
     * 标签
     */
    private String tagList;

    /**
     * 库存
     */
    private Integer inventory;

    /**
     * 房屋描述
     */
    private String detail;

    /**
     * 图片
     */

    private String imageList;


    /**
     * 出租土地平方米
     */
    private Integer land;

    /**
     * 限制
     */
    private String restricted;
    /* *
     * 基础设施 */
    private String basics;
    // private List<HotelFacilityVO> basics;

   /*  *
     * 洗沐设施 */
    private String bath;

  /*   *
     * 电器设施
     */
    private String appliance;


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

    /**
     * 此房型月销量
     */
    private Integer monthlySales;

}
