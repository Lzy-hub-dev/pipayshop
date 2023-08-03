package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jiangjiafeng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateDTO extends PageDTO{

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 评价
     */
    private String evaluate;

}
