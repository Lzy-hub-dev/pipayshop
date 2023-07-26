package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Inherited;

/**
 * @author: jiangjiafeng
 * @ClassName PageDTO
 * @Description 分页属性
 * @date 2023/7/26 16:53
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {
    private Integer pageNumber;
    private Integer pageSize;
}
