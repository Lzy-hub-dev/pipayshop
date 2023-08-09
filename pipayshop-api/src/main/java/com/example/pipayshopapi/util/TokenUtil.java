package com.example.pipayshopapi.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;

/**
 * @author wzx
 */
public class TokenUtil {


    /**
     * 解密Token，拿到内部封装的userId数据
     */
    public static Claims getUserIdFromToken(String token){
        JwtParser jwtParser = Jwts.parser();
        // 通过签名对Token进行解析，得到的结果是一个类似集合的封装类
        Jws<Claims> claimsJws = jwtParser.setSigningKey(Constants.TOKEN_SECRET.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);
        return claimsJws.getBody();
    }



}
