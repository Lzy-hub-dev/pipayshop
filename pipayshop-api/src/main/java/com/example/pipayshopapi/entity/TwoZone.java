package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("two_zone")
public class TwoZone implements Serializable {
    private static final long serialVersionUID = 1L;

    //表ID
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    //所属用户ID
    Long superiorZoneId ;
    //团ID
    Long zoneId;
    //团内人数
    Integer zoneUserNum;
    //开团时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date opening_zone_time;

    public TwoZone(Long superiorZoneId, Long zoneId) {
        this.superiorZoneId = superiorZoneId;
        this.zoneId = zoneId;
    }


}