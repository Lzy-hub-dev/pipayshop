package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 网店评价
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@TableName("shop_evaluate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopEvaluate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 实体店id
     */
    private String itemId;

    /**
     * 评价id
     */
    private String evaluateId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 服务id
     */
    private String commodityId;

    /**
     * 评价
     */
    private String evaluate;

    /**
     * 评价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 评价状态(0发布 1取消)
     */
    private Integer status;


}
