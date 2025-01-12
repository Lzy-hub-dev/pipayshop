package com.example.pipayshopapi.config;

import com.example.pipayshopapi.component.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Resource
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 放行全部接口
                .antMatchers("/pipayshopapi/**","/chat/**").permitAll()
                // 对于登录接口 允许匿名访问
//                .antMatchers("/pipayshopapi/user-info/login").anonymous()
                // 开放b端
//                .antMatchers("/pipayshopapi/shop-info/getShopCodeByShopId/**","/pipayshopapi/b-user-info/**","/pipayshopapi/transaction-record/getRecordTransaction/**","/pipayshopapi/shop-info/getShopList/**").anonymous()
                // 放行图片
                .antMatchers("/images/**").permitAll()
                // 放行websocker
                .antMatchers("/dailyActive/**","/TradinPostSocket/**").permitAll()
                // 放行swagger
                .antMatchers("/swagger-ui.html/**","/webjars/**","/swagger-resources/**","/v2/**").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
