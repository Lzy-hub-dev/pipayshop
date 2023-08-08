package com.example.pipayshopapi.util;

import io.jsonwebtoken.*;

import java.util.Date;

/**
 * @author wzx
 */
public class TokenUtil {

    /**
     * 获取token，将传递的数据封装进JWT的荷载中
     */
    public static String getToken(String userId){
        if (userId == null){return null;}
        JwtBuilder jwtBuilder = Jwts.builder();
        return jwtBuilder
                        .setHeaderParam("typ", "JWT")
                        .setHeaderParam("alg", "HS256")
                        .claim("userId", userId)
                        .setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_INVALID_TIME))
                        .signWith(SignatureAlgorithm.HS256, Constants.TOKEN_SECRET)
                        .compact();
    }

    /**
     * 解密Token，拿到内部封装的userId数据
     */
    public static Claims getUserIdFromToken(String token){
        JwtParser jwtParser = Jwts.parser();
        // 通过签名对Token进行解析，得到的结果是一个类似集合的封装类
        Jws<Claims> claimsJws = jwtParser.setSigningKey(Constants.TOKEN_SECRET).parseClaimsJws(token);
        return claimsJws.getBody();
    }



}
