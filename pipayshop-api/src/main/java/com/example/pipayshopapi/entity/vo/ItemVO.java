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
public class ItemVO {
    private String itemName;
    private Double score;
    private Integer fanSum;
    private String userImage;
    private Integer itemCommoditySum;
    private String itemId;
}
