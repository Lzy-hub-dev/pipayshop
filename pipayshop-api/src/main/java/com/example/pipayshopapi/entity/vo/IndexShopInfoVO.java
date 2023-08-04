package com.example.pipayshopapi.entity.vo;

import com.example.pipayshopapi.entity.ShopTags;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ThinkPad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexShopInfoVO {
    private String shopId;
    private String shopName;
    private Double score;
    private BigDecimal localhostLatitude;
    private String shopIntroduce;
    private BigDecimal localhostLongitude;
    private String userImage;
    private String address;
    private String tagList;
    private List<String> shopTagsList;
}
