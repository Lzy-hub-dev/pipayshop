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
public class CountryVO implements Serializable {

    /**
     * 国家代码
     */
    private String countryCode;

    /**
     * 国家名字
     */
    private String countryName;

}
