package com.example.pipayshopapi.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author wzx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopEvaluateVO {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 服务id
     */
    private String commodityId;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userImage;

    /**
     * 评价
     */
    private String evaluate;

    /**
     * 评价id
     */
    private String evaluateId;

    /**
     * 评价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
