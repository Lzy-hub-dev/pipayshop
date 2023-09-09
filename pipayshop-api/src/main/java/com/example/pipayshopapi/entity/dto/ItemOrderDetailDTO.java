package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author wzx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrderDetailDTO {

    /**
     * 商品的itemId
     */
    private String itemId;
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
     * 商品的规格
     */
    private String commoditySpecification;

    /**
     * 商品的展示图
     */
    private String avatarImag;
}
