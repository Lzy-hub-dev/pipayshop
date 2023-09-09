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
public class CountryMinVO {

    /**
     * 低级行政区域的标识id
     */
    private String countrySecondId;

    /**
     * 低级行政区域的名字
     */
    private String name;
}
