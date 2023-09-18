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
 * 
 * </p>
 *
 * @author wzx
 * @since 2023-09-18
 */
@TableName("user_register")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegister implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String piName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * pi的用户id
     */
    private String uid;

    /**
     * 0:正常1：逻辑删除
     */
    private Integer delFlag;

}
