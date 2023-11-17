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
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zxb
 * @since 2023-07-28
 */
@TableName("item_cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 购物车id
     */
    private String cartId;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 删除标识
     */
    private Integer delFlag;

    /**
     * 总数
     */
    private Integer sumCount;
    /**
     * 商品规格详情（多个规格以逗号隔开）
     */
    private String commoditySpec;
}
