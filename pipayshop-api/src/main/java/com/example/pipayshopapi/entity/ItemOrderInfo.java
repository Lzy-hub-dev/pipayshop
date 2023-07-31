package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商户订单数据表
 * </p>
 *
 * @author zxb
 * @since 2023-07-28
 */
@TableName("item_order_info")
public class ItemOrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 下单用户id
     */
    private String uid;

    /**
     * 卖家id
     */
    private String itemId;

    /**
     * 0:待支付1:已支付2：已完成3：无效订单
     */
    private Boolean orderStatus;

    /**
     * 0:未删除1：逻辑删除2：真实删除
     */
    private Boolean delFlag;

    /**
     * 下单时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 外键关联用户的地址电话等信息方案
     */
    private Long buyerDataId;

}
