package com.example.pipayshopapi.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zxb
 */

@Getter
@AllArgsConstructor
public enum Language {

    LANGUAGE_1(1, "中文"),
    LANGUAGE_2(2,"英语"),
    LANGUAGE_3(3,"西班牙语"),
    LANGUAGE_4(4,"法语"),
    LANGUAGE_5(5,"阿拉伯语"),
    LANGUAGE_6(6,"俄语"),
    LANGUAGE_7(7,"乌尔都"),
    LANGUAGE_8(8,"德语"),
    lLANGUAGE_9(9,"葡萄牙语"),
    lLANGUAGE_10(10,"意大利语")
    ;

    /**
     语言标识
     */
    private final Integer languageId;

    /**
     语言
     */
    private final String language;
}
