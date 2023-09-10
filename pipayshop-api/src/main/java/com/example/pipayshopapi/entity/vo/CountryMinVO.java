package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wzx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryMinVO implements Serializable {

    /**
     * 低级行政区域的标识id
     */
    private String countryId;

    /**
     * 低级行政区域的名字
     */
    private String name;
}
