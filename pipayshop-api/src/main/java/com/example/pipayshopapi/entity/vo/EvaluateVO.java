package com.example.pipayshopapi.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author wzx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluateVO {
    /**
     * 用户头像
     */
    private String userImage;
    /**
     * 用户名字
     */
    private String userName;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 评价内容
     */
    private String evaluate;
    /**
     * 评价的id
     */
    private String evaluateId;
    /**
     * 评价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Data createTime;
}
