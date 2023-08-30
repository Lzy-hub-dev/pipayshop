package com.example.pipayshopapi;

import com.example.pipayshopapi.util.Constants;
import com.example.pipayshopapi.util.FileUploadUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ShopTest {

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
}
