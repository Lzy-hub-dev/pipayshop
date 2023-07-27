package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCommodityEvaluateVO {

    /**
     * 网店id
     */
    private String itemId;

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 评价
     */
    private String evaluate;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userImage;

    /**
     * 评价id
     */
    private String evaluateId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 评价时间
     */
    private Date createTime;
}
