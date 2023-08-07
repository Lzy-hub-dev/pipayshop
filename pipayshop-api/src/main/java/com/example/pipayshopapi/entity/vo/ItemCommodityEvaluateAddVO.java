package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * fileName: ItemCommodityEvaluateAddVO
 * author: 四面神
 * createTime:2023/8/6 16:58
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCommodityEvaluateAddVO {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 评价
     */
    private String evaluate;

    /**
     * 网店id
     */
    private String itemId;

    /**
     * 商品id
     */
    private String commodityId;
    /**
     * 商品评分
     */
    private Double score;

}
