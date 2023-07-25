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
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * item/shop的首页背景轮播图数据
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@TableName("bg_img")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BgImg implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 网店/实体店的首页背景图片id
     */
    private String bgId;

    /**
     * 网店/实体店的首页背景图片的路径
     */
    private String imgUrl;

    /**
     * 0:网点  1:实体店
     */
    private Boolean category;

    /**
     * 创造时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;

    /**
     * 0:启用 1：逻辑删除 2：删除
     */
    private Boolean delFlag;

}
