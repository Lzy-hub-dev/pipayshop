package com.example.pipayshopapi.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author nws
 */

public class StringUtil {

    public static String generateShortId(){

        UUID uuid = UUID.randomUUID();
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();

        // 将 UUID 的最高位和最低位转换成 16 进制字符串
        String mostSigHex = Long.toHexString(mostSigBits);
        String leastSigHex = Long.toHexString(leastSigBits);

        // 拼接最高位和最低位，并截取前11位作为最终的短 ID
        String shortId = mostSigHex + leastSigHex;
        return shortId.substring(0, 11);

    }

    public static void main(String[] args) {
        String s=StringUtil.generateShortId();
        System.out.println(s);
    }

    /**
     * BCrypt加密密码
     */
    public static String hashPassword(String password) {
        // 生成随机盐值
        String salt = BCrypt.gensalt();
        // 生成哈希密码
        return BCrypt.hashpw(password, salt);
    }

    /**
     * 验证密码
      */
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}