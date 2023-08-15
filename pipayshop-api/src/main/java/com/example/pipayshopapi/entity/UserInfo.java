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
 * 用户数据表
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@TableName("user_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private String uid;

    /**
     * Pi 的用户名
     */
    private String piName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登录令牌
     */
    private String accessToken;

    /**
     * 上次登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin;

    /**
     * 个人简介
     */
    private String personalProfile;

    /**
     * 国家标识（枚举类)
     */
    private String country;

    /**
     * 语言标识（枚举类)
     */
    private String language;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 0:普通用户1:商家用户
     */
    private Boolean level;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 0:正常使用1:禁用
     */
    private Boolean status;
    /**
     * 用户头像
     */
    private String userImage;

    /**
     * 性別
     */
    private Integer gender;

    /**
     * 可绑定的实体店数量
     */
    private Integer shopBalance;


}
