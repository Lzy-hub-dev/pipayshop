package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author wzx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityDetailVO {

    private String commodityId;

    private String title;

    private String itemCommodityName;

    private BigDecimal originPrice;

    private Map<String, List<String>> typeMap;

//    private List<String> colorList;

//    private List<String> sizeList;

    private String originAddress;

    private List<String> acceptAddressList;

    private String itemId;

    private BigDecimal price;

    private String details;

    private List<String> imagsList;

    private Integer inventory;

    private Integer freeShippingNum;

    private Integer categoryId;

    private List<String> couponsList;

    private List<String> tagList;

    private Integer degreeLoss;

    private List<String> detailImagList;

    /**
     * 评论总数
     */
    private Integer evaluateCount;

    private ItemVO itemVO;
}
