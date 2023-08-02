package com.example.pipayshopapi.entity.dto;

import java.time.LocalDateTime;

/**
 * @author: jiangjiafeng
 * @ClassName EvaluateDTO
 * @Description TODO
 * @date 2023/8/2 10:50
 * @Version 1.0
 */

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
