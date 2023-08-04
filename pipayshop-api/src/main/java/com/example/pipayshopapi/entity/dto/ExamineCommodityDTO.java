package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jiangjiiafeng
 * @ClassName ExamineCommodityDTO
 * @Description 商品审核列表DTO
 * @date 2023/8/3 19:52
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamineCommodityDTO extends PageDTO{

    private String userId;
    /**
     * 0:审核中;1:审核通过;2全部
     */
    private Integer examineStatus;
}
