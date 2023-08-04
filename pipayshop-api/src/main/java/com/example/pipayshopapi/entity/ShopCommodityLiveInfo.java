package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 酒店的房型表
 * </p>
 *
 * @author zxb
 * @since 2023-08-02
 */
@TableName("shop_commodity_live_info")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShopCommodityLiveInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房型id
     */
    private String roomId;

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
    private String tagList;

    /**
     * 房型图片集合
     */
    private String imageList;

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
    private String basics;

    /**
     * 洗沐设施
     */
    private String bath;

    /**
     * 电器设施
     */
    private String appliance;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 服务状态 0:未删除 1：逻辑删除 2：物理删除
     */
    private Integer delFlag;

    /**
     * 推荐服务 0:不推荐 1：推荐
     */
    private Integer recommended;

    /**
     * 服务头像
     */
    private String avatarImag;

    /**
     * 0:审核 1:上架 2:下架3:绝对删除
     */
    private Integer status;

    /**
     * 床型
     */
    private String bedType;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 是否允许加客 0:可以 1：不可以
     */
    private Integer isAdd;

}
