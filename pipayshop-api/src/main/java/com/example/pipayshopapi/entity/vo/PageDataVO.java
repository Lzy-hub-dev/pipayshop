package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDataVO {

    /**
     * 查到的数据条数
     */
    private Integer count;

    /**
     * 获取的数据
     */
    private List list;

}
