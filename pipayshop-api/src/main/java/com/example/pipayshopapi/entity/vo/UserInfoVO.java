package com.example.pipayshopapi.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Hellow
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO implements Serializable {

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
     * 用户头像
     */
    private String userImage;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 语言标识（枚举类)
     */
    private String language;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 国家标识（枚举类)
     */
    private String country;



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
     * 0:普通用户1:商家用户
     */
    private Integer level;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性別
     */
    private Integer gender;

    /**
     * 可绑定的实体店数量
     */
    private Integer shopBalance;
}
