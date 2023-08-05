package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 品牌分类
 * </p>
 *
 * @author mongdie
 * @since 2023-07-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandInfoVO {

    /**
     * 品牌id
     */
    private String bId;
    /**
     * 品牌
     */
    private String title;
}
