package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPageVO {

    private Integer limit;
    private Integer page;

    private String uid;
    private Integer status;  //0:待支付1:已支付2：已完成3：无效订单

}
