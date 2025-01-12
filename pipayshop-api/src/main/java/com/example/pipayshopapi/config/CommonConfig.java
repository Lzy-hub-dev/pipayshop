package com.example.pipayshopapi.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 服务器端key
 * @author ThinkPad
 */
@Configuration
@Data
public class CommonConfig {

    @Value("${sdk.serverAccessKey}")
    private String serverAccessKey;

    @Value("${image.path}")
    private String imagePath;

    @Value("${PAYPAL.CLIENT_ID}")
    private String PAYPAL_CLIENT_ID;

    @Value("${PAYPAL.APP_SECRET}")
    private String PAYPAL_APP_SECRET;
}