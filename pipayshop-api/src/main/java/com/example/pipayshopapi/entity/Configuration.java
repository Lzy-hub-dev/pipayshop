package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-08-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Configuration implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 0：生效 1：逻辑删除
     */
    private Boolean delFlag;

    /**
     * 配置的信息
     */
    private String content;

    /**
     * 配置的数据
     */
    private String configValue;


}
