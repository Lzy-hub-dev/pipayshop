package com.example.pipayshopapi.util;

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

}