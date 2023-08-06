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
    private String itemId;
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

    private List<ItemCommodityVO> commodityInfoList;
}
