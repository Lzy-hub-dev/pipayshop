package com.example.pipayshopapi.util;

import io.jsonwebtoken.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author wzx
 */
public class TokenUtil {


    /**
     * 获取token，将传递的数据封装进JWT的荷载中
     */
    public static String getToken(String piName){
        if (piName == null){return null;}
        JwtBuilder jwtBuilder = Jwts.builder();
        return jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("pi_name", piName)
                .setExpiration(new Date(System.currentTimeMillis() + Constants.USER_ACTIVE_TIME))
                .signWith(SignatureAlgorithm.HS256, Constants.TOKEN_SECRET)
                .compact();
    }

    /**
     * 解密Token，拿到内部封装的userId数据
     */
    public static Claims getDataFromToken(String token){
        JwtParser jwtParser = Jwts.parser();
        // 通过签名对Token进行解析，得到的结果是一个类似集合的封装类
        Jws<Claims> claimsJws = jwtParser.setSigningKey(Constants.TOKEN_SECRET.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);
        return claimsJws.getBody();
    }

    /**
     * 解密Token，拿到内部封装的userId数据，不同字节数组进行匹配
     */
    public static Claims getDataFromToken2(String token){
        JwtParser jwtParser = Jwts.parser();
        // 通过签名对Token进行解析，得到的结果是一个类似集合的封装类
        Jws<Claims> claimsJws = jwtParser.setSigningKey(Constants.TOKEN_SECRET).parseClaimsJws(token);
        return claimsJws.getBody();
    }


}
