package com.example.pipayshopapi.entity.vo;

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
public class ItemCollectionVO {

    /**
     * 商品现价
     */
    private BigDecimal price;
    /**
     * 商品名字
     */
    private String itemCommodityName;
    /**
     * 网店名字
     */
    private String itemName;
    /**
     * 商品首图
     */
    private String commodityPic;
    /**
     * 网店首图
     */
    private String sellerPic;
    /**
     * 商品id
     */
    private String commodityId;
    /**
     * 是否会员(0映射为false，1映射为true)
     */
    private Boolean membership;
}
