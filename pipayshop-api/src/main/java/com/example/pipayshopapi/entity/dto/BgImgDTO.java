package com.example.pipayshopapi.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author: jiangjiafeng
 * @ClassName BgImgDTO
 * @Description 首页轮播图dto
 * @date 2023/8/5 19:29
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BgImgDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 0:网点  1:实体店
     */
    @ApiModelProperty(value = "0:网点  1:实体店",required = true)
    private Integer category;

    /**
     * 该图片对应的内容的id (网店商品id/实体店铺id)
     */
    @ApiModelProperty(value = "网店商品id/实体店铺id",required = true)
    private String contentId;


    /**
     * 网店/实体店的首页背景图片的路径
     */
    @ApiModelProperty(value = "网店/实体店的首页背景图片的路径",required = true)
    private String imgUrl;
}
