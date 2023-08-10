package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@TableName("item_commodity_evaluate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCommodityEvaluate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 网店id
     */
    private String itemId;

    /**
     * 商品id
     */
    private String commodityId;



    /**
     * 评价id
     */
    private String evaluateId;

    /**
     * 用户id
     */
    private String userId;



    /**
     * 评价
     */
    private String evaluate;

    /**
     * 评价时间
     */
    private Date createTime;

    /**
     * 评价状态(0关注 1取消)
     */
    private Integer status;

    /**
     * 评分
     */
    private Double score;


}
