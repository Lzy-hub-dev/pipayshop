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
     标识id -1：所有订单   0：未支付订单    1：已支付订单   2：已完成（已经收货）订单
     */
    private Integer categoryId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 当前页码
     */
    private Integer currentPage = 1;
}
