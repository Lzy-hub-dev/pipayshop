package com.example.pipayshopapi.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: jiangjiafeng
 * @ClassName ItemCommodityVO
 * @Description TODO
 * @date 2023/8/3 19:40
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCommodityVO {
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
     * 商品状态0:审核 1:上架 2:下架3:绝对删除
     */
    private String status;
    /**
     * 网店所有者id
     */
    @JsonIgnore
    private String userId;
}
