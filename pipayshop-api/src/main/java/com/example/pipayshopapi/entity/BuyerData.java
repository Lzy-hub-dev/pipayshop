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
import java.util.Date;

/**
 * <p>
 * 买家的基本数据（多选）
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@TableName("buyer_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 收货数据id
     */
    private String buyerDataId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 收货人的名字
     */
    private String userName;


    /**
     * 收货人的手机号
     */
    private String phone;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    /**
     * 0:正常1逻辑删除2：绝对删除
     */
    private Boolean delFlag;

    /**
     * 0:正常1逻辑删除2：绝对删除
     */
    private Integer isDefault;
}
