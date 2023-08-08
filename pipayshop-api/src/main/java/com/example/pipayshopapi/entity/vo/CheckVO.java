package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wzx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckVO {

    private String shopId;

    private String shopName;

    private String userImage;
}
