package com.example.pipayshopapi.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private String sellerId;
    private Integer typeId;
    private String content;
    private BigDecimal pointBalance;
    private BigDecimal piBalance;
    private String userName;
    private String userImage;
    private String piAddress;

    
}
