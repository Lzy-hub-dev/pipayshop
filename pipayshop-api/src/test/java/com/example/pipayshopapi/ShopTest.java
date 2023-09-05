package com.example.pipayshopapi;

import com.example.pipayshopapi.config.CommonConfig;
import com.example.pipayshopapi.util.Constants;
import com.example.pipayshopapi.util.FileUploadUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;

public class ShopTest {

    CommonConfig commonConfig = new CommonConfig();

    @Test
    public void imageTest(){
        try {
            String path = "/images/avatar/928aa28a82c.jpg";
            List<String> list = new ArrayList<>();
            list.add("200,200");
            FileUploadUtil.asyCropping(path,list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }







    @Test
    public void test01(){



    }

    @Test
    public void testss(){
        try {
            // 原始图片路径
            File originalImage = new File("C:\\Users\\Hellow\\Desktop\\pi-pay-shop-api\\pipayshop-api\\src\\main\\resources\\static\\images\\avatar\\avatar.jpg");

            // 压缩后图片路径
            File compressedImage = new File("C:\\Users\\Hellow\\Desktop\\pi-pay-shop-api\\pipayshop-api\\src\\main\\resources\\static\\images\\avatar\\avatar2.jpg");

            // 设置压缩后的图片大小
            int targetWidth = 100;
            int targetHeight = 100;

            Thumbnails.of(originalImage)
                    .size(targetWidth, targetHeight)
                    .outputQuality(1.0) // 保持原始图片质量
                    .toFile(compressedImage);

            System.out.println("图片压缩完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testss2() {
            File readPath = new File("pipayshop-api/src/main/resources/static/images/avatar");
            System.out.println(readPath.getAbsolutePath());

    }
}
