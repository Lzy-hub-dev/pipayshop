package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * fileName: HotelFacilityVO
 * author: 四面神
 * createTime:2023/8/4 13:58
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelFacilityVO {

    /**
     * 设施名称
     */
    String name;

    /**
     * true：拥有 false：未拥有
     */
    Boolean flag;
}
