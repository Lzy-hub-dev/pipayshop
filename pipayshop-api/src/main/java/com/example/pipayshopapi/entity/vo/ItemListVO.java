package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wzx
 * 网店列表展示的VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemListVO {
    /**
     * 网店id
     */
    private String itemId;
    /**
     * 网店名称
     */
    private String itemName;
    /**
     * 是否会员(0映射为false，1映射为true)
     */
    private Boolean membership;
    /**
     * 网店评分
     */
    private Double score;
    /**
     * 用户头像
     */
    private String userImage;
}
