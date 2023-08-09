package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxb
 * @since 2023-08-09
 */
@TableName("b_user_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    /**
     * 实体店商家用户的id
     */
    private String userId;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 开户时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date uodateTine;

    /**
     * 0:正常1：警禁用
     */
    private Integer status;




}
