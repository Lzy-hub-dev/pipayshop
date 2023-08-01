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
 * 网店商品收货地址
 * </p>
 *
 * @author zxb
 * @since 2023-07-31
 */
@TableName("buyer_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
     * 收货人名字
     */
    private String userName;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 0:正常1逻辑删除2：绝对删除
     */
    private Boolean delFlag;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 默认地址( 0：非默认 1：默认地址)
     */
    private Integer isDefault;

}
