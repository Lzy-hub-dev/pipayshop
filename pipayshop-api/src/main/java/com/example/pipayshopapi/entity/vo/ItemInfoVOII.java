package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
/**
 * fileName: ItemInfoVOII
 * author: 四面神
 * createTime:2023/8/5 10:22
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfoVOII extends ItemInfoVO{
    List<String> imageList;
}
