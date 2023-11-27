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
    BigDecimal thresholdSum;
    //任务完成时间
    Integer taskTime;
    //下级最大人数
    Integer subordinateMaxNum;
    //一级利率
    Long firstInterestRate;
    //二级利率
    Long twoInterestRate;
    // 二级利率二级时间利率
    Long twoInterestRateTwo;
    //是否开启二级返佣时间（0：不开启 1：开启）
    Integer twoRebateTime;
    //二级返佣二级时间的一级天数
    Integer twoRebateTimeOne;
    //二级返佣二级时间的二级天数
    Integer twoRebateTimeTwo;
}
