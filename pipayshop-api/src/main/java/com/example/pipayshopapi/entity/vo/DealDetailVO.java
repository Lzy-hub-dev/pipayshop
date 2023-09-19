package com.example.pipayshopapi.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealDetailVO {
    private String tradinId;
    private String publisherUid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String traderUid;
    private Integer typeId;
    private String content;
    private BigDecimal pointBalance;
    private BigDecimal piBalance;
    private String publisherName;
    private String publisherImage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String traderName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String traderImage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String piAddress;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String imageUrl;

}
