package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraditionListVO {
    private String tradinId;
    private String publisherUid;
    private Integer typeId;
    private String content;
    private BigDecimal pointBalance;
    private BigDecimal piBalance;
    private String userName;
    private String userImage;
}
