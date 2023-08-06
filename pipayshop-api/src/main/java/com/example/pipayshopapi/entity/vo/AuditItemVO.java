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
public class AuditItemVO {

    /**
     * 商品现价
     */
    private BigDecimal price;
    /**
     * 商品名字
     */
    private String itemCommodityName;
    /**
     * 商品首图
     */
    private String avatarImag;
    /**
     * 商品id
     */
    private String commodityId;
    /**
     * 商品状态0:审核 1:上架 2:下架3:绝对删除
     */
    private String status;
}
