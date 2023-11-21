package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("log_member_consumption")
public class LogMemberConsumption implements Serializable {
    private static final long serialVersionUID = 1L;
    //表id
    Integer id;
    //用户ID
    String user_id;
    //所属团ID
    BigInteger zone_id;
    //消费金额
    BigDecimal consumption_sum;
    //消费时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date consumption_time;
}
