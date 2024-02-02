package com.example.pipayshopapi.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zxb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyShopDTO {
    private BigDecimal localhostLatitude;
    private BigDecimal localhostLongitude;
    private String address;
    private String shopName;
    private String phone;
    private String shopIntroduce;

    private List<String> shopImagList;

    private String categoryId;
    private String uid;
    private Integer uploadCommodityBalance;

    private String qrcode;

    /**
     * 外键关联的区域id
     */
    private String regionId;

    private String piratio;


    private String startTime;

    private String endTime;
}
