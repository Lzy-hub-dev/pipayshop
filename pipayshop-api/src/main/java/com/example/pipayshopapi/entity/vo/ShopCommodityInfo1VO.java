package com.example.pipayshopapi.entity.vo;

import com.example.pipayshopapi.entity.ShopTags;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCommodityInfo1VO {
    /**
     * 商品id
     */
    private String commodityId;
    /**
     * 商店的商品名字
     */
    private String shopCommodityName;
    /**
     * 实体店商品的头像
     */
    private String avatarImag;
    /**
     * 商品的价格
     */
    private BigDecimal price;

    /**
     * 商品的月收量
     */
    private Integer monthlySales;

    /**
     * 标签集合
     */
    private String tagList;
    /**
     * 标签列表
     */
    private List<ShopTags> shopTagsList;
}
