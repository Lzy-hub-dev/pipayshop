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
     * 用户id
     */
    private String uid;

    /**
     * 收货人的名字
     */
    private String userName;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 收货人的手机号
     */
    private String phone;

    /**
     * 0:正常1逻辑删除2：绝对删除
     */
    private Boolean delFlag;

}
