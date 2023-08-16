package com.example.pipayshopapi.Interceptor;

import com.example.pipayshopapi.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author wzx
 */
@Deprecated
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求中获取 Token
        String token = request.getHeader("Authorization");
        // 校验 Token
        if (isValidToken(token)) {
            // Token 校验通过，允许请求继续访问接口
            return true;
        } else {
            // Token 校验不通过，返回错误信息或者重定向到登录页面等操作
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return false;
        }
    }

    /**
     * 校验token
     */
    private boolean isValidToken(String token) {
        if (token == null || "".equals(token)){
            return false;
        }
        try {
            //  我们并不需要拿出claimsJws的信息，只是说解析出来claimsJws的这一步操作没有报错，那么就证明底层的密钥是正确的
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(Constants.TOKEN_SECRET).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
