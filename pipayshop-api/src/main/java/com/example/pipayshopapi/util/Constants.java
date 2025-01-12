package com.example.pipayshopapi.util;
/**
 * @author wzx
 * 常量类
 */
public class Constants {

    /**
     用户登录的失效期限是24小时
     */
    public static final Integer USER_ACTIVE_TIME = 24 * 60 * 60 * 1000 ;

    /**
     * JWT密钥
     */
    public static final String TOKEN_SECRET = "pairuan";

    /**
     * 用户和网店的默认头像id
     */
    public static final String AVATAR_IMAG = "defaultAvatar";

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
     * 四级国家行政分区的redis存储Key
     */
    public static final String  COUNTRY_FOURTH_REGION = "country_fourth_region";

    /**
     * 国家行政分区的redis存储时间（也是定期更新缓存的时间）,默认为一天
     */
    public static final Integer REGION_VALID_TIME = 60 * 60 * 24;


    /**
     * 保存交易所图片凭证路径
     */
    public static final String CERTIFICATE_IMAG_PATH = "pipayshop-api/src/main/resources/static/images/tradin_post_certificate";
    /**
     * 保存pi交易图片凭证路径
     */
    public static final String PAYMENT_IMAG_PATH = "pipayshop-api/src/main/resources/static/images/payment_certificate";
    /**
     * 校验码的存储key前缀
     */
    public static final String CHECK_CODE_PRE = "check_";

    /**
     * 校验码的存储时间(三分钟)
     */
    public static final Long CHECK_CODE_VALID_TIME  = 60 * 3L;

    /**
     * PAYPAL接口路径
     */
    public static final String PAYPAL_BASE_PATH = "https://api-m.paypal.com";
    /**
     * 交易所订单延时交换机
     */
    public static final String DELAYED_EXCHANGE_NAME = "tradinOrderDelayed.exchange";
    /**
     * 交易所订单延时队列
     */
    public static final String DELAYED_QUEUE_NAME = "tradinOrderDelayed.queue";
    /**
     * 交易所订单延时关键字
     */
    public static final String DELAYED_ROUTING_KEY = "tradinOrderDelayed.routingkey";






}
