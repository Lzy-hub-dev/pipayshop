package com.example.pipayshopapi.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraditionDetailVO {
    private String tradinId;

    private String publisherUid;
    private Integer typeId;
    private String content;
    private BigDecimal pointBalance;
    private BigDecimal piBalance;
    private String userName;
    private String userImage;
    private String piAddress;

    
}
