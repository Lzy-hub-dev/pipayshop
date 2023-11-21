package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("first_zone")
public class FirstZone implements Serializable {
    private static final long serialVersionUID = 1L;

    //表ID
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    //所属用户ID
    String user_id;
    //团ID
    BigInteger zone_id;
    //团内人数
    Integer zone_user_num;
    //开团时间
    Date opening_zone_time;
    //是否返利
    Integer level_rebate;
}
