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
@TableName("two_zone_superior")
public class TwoZoneSuperior implements Serializable {
    private static final long serialVersionUID = 1L;

    //表ID
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    //所属用户ID
    String userId;
    //关联的一级团ID
    Long firstZoneId;
    //团ID
    Long zoneId;
    //二级团名称
    String zoneName;
    //开团时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date openingZoneTime;
    //二级团数
    Integer zoneNum;
    //是否返利
    Integer levelRebate;
    //返佣资格
    Integer rebateQualification;
    //团一级失效时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date endTimeOne;
    //团二级失效时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date endTimeTwo;
    //创团时是否开启二级返佣二级时间
    Integer isTwoRebateTime;
    //是否失效
    Integer invalid;
}
