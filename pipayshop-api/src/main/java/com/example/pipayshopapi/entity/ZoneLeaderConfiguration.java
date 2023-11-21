package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ZoneLeaderConfiguration implements Serializable {
    private static final long serialVersionUID = 1L;

    //表id
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    //团长门槛金额
    BigDecimal ThresholdSum;
    //任务完成时间
    Integer TaskTime;
    //下级最大人数
    Integer SubordinateMaxNum;
    //一级利率
    BigDecimal FirstInterestRate;
    //二级利率
    BigDecimal TwoInterestRate;
    //是否开启二级返佣时间（0：不开启 1：开启）
    Integer twoRebateTime;
}
