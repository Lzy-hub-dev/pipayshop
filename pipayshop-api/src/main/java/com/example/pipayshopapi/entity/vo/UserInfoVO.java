package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hellow
 */
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
     * 语言标识
     */
    private String language;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 國家
     */
    private String country;


}
