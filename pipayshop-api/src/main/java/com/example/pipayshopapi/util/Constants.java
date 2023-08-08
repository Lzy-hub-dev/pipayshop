package com.example.pipayshopapi.util;
/**
 * @author wzx
 * 常量类
 */
public class Constants {

    /**
     用户登录的失效期限是3小时
     */
    public static final Integer USER_ACTIVE_TIME = 180 * 60;

    /**
     * JWT密钥
     */
    public static final String TOKEN_SECRET = "pairuan";

    /**
     * JWT有效期(默认10min)
     */
    public static final long TOKEN_INVALID_TIME = 1000 * 60 * 10L;
}
