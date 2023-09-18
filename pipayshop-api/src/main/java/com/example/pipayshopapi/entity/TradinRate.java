package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author wzx
 * @since 2023-09-14
 */
@TableName("tradin_rate")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradinRate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 汇率id
     */
    private Integer id;

    /**
     * 转化货币
     */
    private String title;

    /**
     * 单位
     */
    private String format;

    /**
     * 汇率
     */
    private BigDecimal conversionRate;

    /**
     * 0：启用 1：禁用
     */
    private String status;


}
