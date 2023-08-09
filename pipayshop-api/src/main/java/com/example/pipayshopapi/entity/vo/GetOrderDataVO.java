package com.example.pipayshopapi.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wzx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderDataVO {

    /**
     标识id -1：所有订单   0:待支付1:已支付2：已完成3：无效订单4:已评价
    */
    private Integer orderStatus;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 实体店id
     */
    private String shopId;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 当前页码
     */
    private Integer currentPage = 1;


}
