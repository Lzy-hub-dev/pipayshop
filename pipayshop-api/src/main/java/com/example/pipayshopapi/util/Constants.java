package com.example.pipayshopapi.util;
/**
 * @author wzx
 * 常量类
 */
public class Constants {

    /**
     用户登录的失效期限是8小时
     */
    public static final Integer USER_ACTIVE_TIME = 8 * 60 * 60 * 1000 ;

    /**
     * JWT密钥
     */
    public static final String TOKEN_SECRET = "pairuan";

    /**
     * 用户和网店的默认头像
     */
    public static final String AVATAR_IMAG = "/images/avatar/avatar.jpg";

    /**
     * 全局统一的图片格式
     */
    public static final String IMAGE_TYPE = "png";

    /**
     * 一级国家行政分区的redis存储Key
     */
    public static final String COUNTRY_REGION = "country_region";

    /**
     * 二级国家行政分区的redis存储Key
     */
    public static final String COUNTRY_SECOND_REGION = "country_second_region";

    /**
     * 三级国家行政分区的redis存储Key
     */
    public static final String COUNTRY_THIRD_REGION = "country_third_region";

    /**
     * 国家行政分区的redis存储时间（也是定期更新缓存的时间）,默认为一天
     */
    public static final Integer REGION_VALID_TIME = 60 * 60 * 24;
}
