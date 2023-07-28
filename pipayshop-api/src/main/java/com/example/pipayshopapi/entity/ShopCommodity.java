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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 实体店的商品表
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@TableName("shop_commodity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCommodity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 店铺商品的id
     */
    private String commodityId;

    /**
     * 商品的名字
     */
    private String commodityName;

    /**
     * 商品的图片路径集合
     */
    private String commodityImgList;

    /**
     * 商品详情
     */
    private String commodityDetail;

    /**
     * 商品的价格
     */
    private BigDecimal price;

    /**
     * 商品的月收量
     */
    private Integer monthlySales;



    /**
     * 该商品标签集合
     */
    private String tagList;

    /**
     * 该商品的店铺id
     */
    private String shopId;

    /**
     * 商品上架的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     *商品数据修改的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 商品有效期
     */
    private Integer validityTime;

    /**
     * 商品剩余数量
     */
    private Integer residue;

    /**
     * 预定的注意事项
     */
    private String reservationInformation;

    /**
     * 0:正常1:下架2:绝对删除
     */
    private Boolean status;


}
