package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wzx
 * @since 2023-09-07
 */
@TableName("country_third")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CountryThird implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 三级行政区域的标识id
     */
    private String countryThirdId;

    /**
     * 三级行政区域的名字
     */
    private String name;

    /**
     * 父级分类的外键
     */
    private String pidId;

    /**
     * 0:
     */
    private Integer delFlag;


}
