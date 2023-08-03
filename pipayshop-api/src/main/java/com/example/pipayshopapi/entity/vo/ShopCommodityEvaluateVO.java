package com.example.pipayshopapi.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author: jiangjiafeng
 * @ClassName ShopCommodityEvaluateVO
 * @Description TODO
 * @date 2023/8/2 10:29
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCommodityEvaluateVO {
    /**
     * 网店id
     */
    private String itemId;

    /**
     * 商品id
     */
    private String commodityId;
    /**
     * 商品名称
     */
    private String commodityName;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
