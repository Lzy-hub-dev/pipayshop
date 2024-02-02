package com.example.pipayshopapi.entity;

import lombok.Data;

@Data
public class ExchangeRate {
    //
    String from;
    String to;
    String to_name;
    String exchange;
    //
    String money;
    // 更新时间
    String updatetime;
}
