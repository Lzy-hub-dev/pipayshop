package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {

    /**
     * 用户id
     */
    private String uid;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 个人简介
     */
    private String personalProfile;
    /**
     * 语言标识
     */
    private String language;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性別
     */
    private Integer gender;

    /**
     * 用户头像
     */
    private String userImage;

    /**
     * 國家
     */
    private String country;


}
