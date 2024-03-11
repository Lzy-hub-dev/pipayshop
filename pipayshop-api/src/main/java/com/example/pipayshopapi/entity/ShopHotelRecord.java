package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 记录酒店入住订单信息表
 * </p>
 *
 * @author zxb
 * @since 2023-08-02
 */
@TableName("shop_hotel_record")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShopHotelRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 酒店记录信息id
     */
    private String recordId;

    /**
     * 房型id （外键）
     */
    private String roomId;

    /**
     * 入住人账号
     */
    private String uid;

    /**
     * 入住人姓名
     */
    private String name;

    /**
     * 入住人电话
     */
    private String phone;

    /**
     * 入住日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 离店日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 记录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date currentTime;

    /**
     * 0：生效 1：逻辑删除
     */
    private Integer delFlag;

    /**
     * 订单id
     */
    private String orderId;
}
