package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZoneStatusVO {
    //返利资格
    Integer rebateQualification;
    //是否失效
    Integer invalid;
}
