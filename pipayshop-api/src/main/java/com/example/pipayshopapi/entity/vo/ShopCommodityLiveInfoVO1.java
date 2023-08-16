package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCommodityLiveInfoVO1 {
    /**
     * 房型名称
     */
    private String roomTypeName;

    /**
     * 所属实体店id
     */
    private String shopId;

    /**
     * 房型库存
     */
    private Integer inventory;

    /**
     * 房型描述
     */
    private String detail;

    /**
     * 房型标签集合
     */
    private List<String> tagList;

    /**
     * 房型图片集合
     */
    private List<String> imageList;
    /**
     * 出租土地平方米
     */
    private Integer land;

    /**
     * 几间房
     */
    private Integer room;

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
     * 限制
     */
    private String restricted;

    /**
     * 基础设施
     */
    private List<NameFlagVO> basics;

    /**
     * 洗沐设施
     */
    private List<NameFlagVO> bath;

    /**
     * 电器设施
     */
    private List<NameFlagVO> appliance;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 床型
     */
    private String bedType;

    /**
     * 楼层
     */
    private String floor;

    /**
     * 是否允许加客 0:可以 1：不可以
     */
    private Integer isAdd;

}
