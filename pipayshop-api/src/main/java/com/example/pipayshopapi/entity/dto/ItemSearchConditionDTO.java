package com.example.pipayshopapi.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zxb
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSearchConditionDTO {

    /**
     * 品牌id
     */
    private String brandId;
    /**
     * 商品名称（模糊查询）
     */
    private String commodityName;

    /**
     * 折损率 枚举类
     */
    private Integer degreeLoss;

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 是否免运费(0:是,1:否)
     */
    @ApiModelProperty("是否免运费(0:是,1:否)")
    private Integer freeShippingNum;
    /**
     * 一页多少条数据
     */
    private Integer limit;
    /**
     * 价格升降序
     */
    @ApiModelProperty("价格升降序(0:升序,1:否降序)")
    private Integer priceOrder;
    /**
     * 发布时间
     */
    private Boolean createTime;

}
