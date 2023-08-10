package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.ws.soap.Addressing;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author ThinkPad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCommodityLiveInfoListVO {
    private String roomId;
    private String roomTypeName;
    private String tagList;
    private Integer land;
    private BigDecimal price;
    private String avatarImag;
    private Integer inventory;
    private String bedType;
}
