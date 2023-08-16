package com.example.pipayshopapi.config;

import com.example.pipayshopapi.Interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:pipayshop-api/src/main/resources/static/images/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//         添加B端拦截器，并指定拦截的路径
        registry.addInterceptor(new TokenInterceptor())
                .addPathPatterns("/pipayshopapi/transaction-record/getRecordTransaction/**", "/pipayshopapi/transaction-record/recordTransaction/**");
    }
}