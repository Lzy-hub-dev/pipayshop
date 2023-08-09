package com.example.pipayshopapi.entity.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * fileName: HotelInfoVO
 * author: 四面神
 * createTime:2023/8/9 11:50
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelInfoVO {

    private String shopId;
    private String shopName;
    private String tagList;
    private String address;
    private Double score;
    private String shopIntroduce;
    private String userImage;
}
