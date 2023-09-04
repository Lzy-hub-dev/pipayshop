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
            File file = ResourceUtils.getFile("classpath:static/images/avatar/59ebedfefb3.jpg");
            System.out.println(file.getAbsolutePath().replace("target\\classes", "src\\main\\resources"));
            String newFileName = file.getAbsolutePath().replace("target\\classes", "src\\main\\resources").replaceFirst("\\.([a-z]+)", "_" + 66 +"_" + 66 + ".jpeg");

            Thumbnails.of(file)
                    .size(80, 80)
                    .outputFormat(Constants.IMAGE_TYPE)
                    .toFile(newFileName);
            System.out.println(newFileName);
            System.out.println(new File(newFileName).exists());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testss2() {
            File readPath = new File("pipayshop-api/src/main/resources/static/images/avatar");
            System.out.println(readPath.getAbsolutePath());

    }
}
