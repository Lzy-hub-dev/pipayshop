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
import java.math.BigInteger;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("log_leader_commission")
public class LogLeaderCommission implements Serializable {
    private static final long serialVersionUID = 1L;

    //表id
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    //团长用户ID
    BigInteger zone_leader_id;
    //所属团ID
    BigInteger zone_id;
    //返佣金额
    BigInteger commission_amount;
    //返佣发放时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date commission_time;

}
