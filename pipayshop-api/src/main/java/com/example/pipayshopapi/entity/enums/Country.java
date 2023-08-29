package com.example.pipayshopapi.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author nws
 */

@Getter
@AllArgsConstructor
public enum Country {

    COUNTRY_CHINA("china_commodity", "中国"),
    COUNTRY_ENGLAND("england_commodity", "英国"),
    COUNTRY_SPAIN("spain_commodity", "西班牙"),
    COUNTRY_FRANCE("france_commodity", "法国"),
    COUNTRY_ARAB("arab_commodity", "阿拉伯"),
    COUNTRY_RUSSIA("russia_commodity", "俄罗斯"),
    COUNTRY_PAKISTAN("pakistan_commodity", "巴基斯坦"),
    COUNTRY_GERMANY("germany_commodity", "德国"),
    COUNTRY_PORTUGAL("portugal_commodity", "葡萄牙"),
    COUNTRY_ITALY("italy_commodity", "意大利"),
    COUNTRY_AMERICA("america_commodity", "美国");

    /**
     国家在redius中对应的桶的名字
     */
    private final String bucketName;

    /**
     国家
     */
    private final String country;

    /**
     * 获取所有的枚举元素
     */
    public static Country[] getAllTypes() {
        return values();
    }

}
