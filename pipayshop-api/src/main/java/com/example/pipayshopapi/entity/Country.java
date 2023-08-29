package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-08-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 国家代码
     */
    private String countryCode;

    /**
     * 国家名字
     */
    private String countryName;

    /**
     * 0:正常 1：逻辑删除
     */
    private Integer delFlag;

}
