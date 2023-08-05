package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: jiangjiafeng
 * @ClassName ApplicationRecordVO
 * @Description 商品审核列表VO
 * @date 2023/8/5 16:30
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRecordVO {
    /**
     * 商品id
     */
    private String commodityId;
    /**
     * 商店的商品名字
     */
    private String commodityName;
    /**
     * 商店的商品第一张照片
     */
    private String avatarImag;
    /**
     * 商品的状态
     */
    private Integer status;
    /**
     * 商品的价格
     */
    private BigDecimal price;
}
