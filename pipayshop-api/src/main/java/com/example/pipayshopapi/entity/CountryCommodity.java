package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxb
 * @since 2023-08-29
 */
@TableName("country_commodity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryCommodity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 国家代码
     */
    private String countryCode;

    /**
     * 网店商品的id
     */
    private String commodityId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 0:正常 1：逻辑删除
     */
    private Integer delFlag;

}
