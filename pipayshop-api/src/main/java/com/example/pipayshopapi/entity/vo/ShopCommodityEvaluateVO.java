package com.example.pipayshopapi.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author: jiangjiafeng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCommodityEvaluateVO {
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

    /**
     * 服务评分
     */
    private Double score;
}
