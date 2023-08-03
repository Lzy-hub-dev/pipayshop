package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * fileName: ItemMinInfoVo
 * author: 四面神
 * createTime:2023/8/3 19:23
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemMinInfoVo {

    /**
     * 网店ID
     */
    private Integer itemId;

    /**
     * 商品数量
     */
    private Integer num;

}
