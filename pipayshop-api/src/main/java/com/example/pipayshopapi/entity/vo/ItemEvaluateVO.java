package com.example.pipayshopapi.entity.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * fileName: ItemEvaluateVO
 * author: 四面神
 * createTime:2023/8/5 12:09
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemEvaluateVO {

    /**
     * 网店id
     */
    private String itemId;

    /**
     * 网店名称
     */
    private String itemName;

    /**
     * 评分
     */
    private double score;

    /**
     * 网店评价
     */
    private List<EvaluateVO> evaluateVOList;

}
