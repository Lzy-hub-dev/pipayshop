package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ThinkPad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemInfoVO {

    /**
     * 网店id
     */
    private String itemId;

    /**
     * 用户头像
     */
    private String userImage;

    /**
     * 网店名称
     */
    private String itemName;

    /**
     * 评分
     */
    private double score;

    /**
     * 网店数量
     */
    private Integer itemSum;

    /**
     * 粉丝总数
     */
    private Integer fanSum;

    /**
     * 网店图片列表
     */
    private String itemImagList;

    /**
     * 是否会员(0映射为false，1映射为true)
     */
    private Boolean membership;

    /**
     * 网店旗下所属商品信息
     */
    private List<ItemCommodityVO> commodityInfoList;
}
