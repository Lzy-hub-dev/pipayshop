package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:
 * @ClassName ShopCommodityEvaluateDTO
 * @Description
 * @date 2023/8/7 18:31
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCommodityEvaluateDTO {

    /**
     * 服务id
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


    /**
     * 评分
     */
    private Double score;

    /**
     * 订单Id
     */
    private String orderId;
}
