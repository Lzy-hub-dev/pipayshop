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
    private String itemName;
    private double score;
    private Integer itemSum;
    private Integer fanSum;
    private String itemImagList;
    private List<ItemCommodityVO> commodityInfoList;
}
