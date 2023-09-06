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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wzx
 * @since 2023-09-05
 */
@TableName("item_order_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联的订单id
     */
    private String orderId;

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 下单时的单价
     */
    private BigDecimal price;

    /**
     * 优惠后的单价
     */
    private BigDecimal discount;

    /**
     * 该商品的下单数量
     */
    private Integer number;

    /**
     * 商品的展示图路径
     */
    private String avatarImag;

    /**
     * 商品的规格
     */
    private String commoditySpecification;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 0:未删除1：逻辑删除
     */
    private Boolean delFlag;


}
