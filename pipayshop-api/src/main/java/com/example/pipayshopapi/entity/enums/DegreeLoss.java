package com.example.pipayshopapi.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DegreeLoss {
    COUNTRY_1(1, "一层新"),
    COUNTRY_2(2, "二层新"),
    COUNTRY_3(3, "三层新"),
    COUNTRY_4(4, "四层新"),
    COUNTRY_5(5, "五层新"),
    COUNTRY_6(6, "六层新"),
    COUNTRY_7(7, "七层新"),
    COUNTRY_8(8, "八层新"),
    COUNTRY_9(9, "九层新"),
    COUNTRY_10(10, "十层新");

    /**
     商品的折损标识
     */
    private final Integer degreeLossId;

    /**
     商品的折损
     */
    private final String degreeLoss;


}
