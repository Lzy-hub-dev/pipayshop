package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 用户账户表
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@TableName("account_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 积分余额
     */
    private BigDecimal pointBalance;

    /**
     * pi币余额
     */
    private BigDecimal piBalance;

    /**
     * 0:未删除1:逻辑删除2:真实删除
     */
    private Boolean delFlag;

}
