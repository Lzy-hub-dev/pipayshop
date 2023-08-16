package com.example.pipayshopapi.util;
/**
 * @author wzx
 * 常量类
 */
public class Constants {

    /**
     用户登录的失效期限是8小时
     */
    public static final Integer USER_ACTIVE_TIME = 60 * 1000;

    /**
     * JWT密钥
     */
    public static final String TOKEN_SECRET = "pairuan";

    /**
     * 用户和网店的默认头像
     */
    public static final String AVATAR_IMAG = "/images/avatar/avatar.jpg";
}
