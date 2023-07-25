package com.example.pipayshopapi.util;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;


public class Md5Util {

    public static String turnMd5(String text){
        String md5 = "  ";
        try {
            md5 = DigestUtils.md5DigestAsHex(text.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return md5;
    }

    public static void main(String[] args) {

        System.out.println(Md5Util.turnMd5("123456"));
        System.out.println(new Date());


    }

}
