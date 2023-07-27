package com.example.pipayshopapi.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author nws
 */

@Getter
@AllArgsConstructor
public enum Country {

    COUNTRY_1(1, "中国"),
    COUNTRY_2(2, "英国"),
    COUNTRY_3(3, "西班牙"),
    COUNTRY_4(4, "法国"),
    COUNTRY_5(5, "阿拉伯"),
    COUNTRY_6(6, "俄罗斯"),
    COUNTRY_7(7, "巴基斯坦"),
    COUNTRY_8(8, "德国"),
    COUNTRY_9(9, "葡萄牙"),
    COUNTRY_10(10, "意大利");


    /**
     国家标识
     */
    private final Integer countryId;

    /**
     国家
     */
    private final String country;

}
