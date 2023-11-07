package com.example.pipayshopapi;

import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.config.CommonConfig;
import com.example.pipayshopapi.entity.ItemOrder;
import com.example.pipayshopapi.entity.TradinPost;
import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.entity.dto.ItemOrderDetailDTO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.TradinPostMapper;
import com.example.pipayshopapi.util.*;
import io.jsonwebtoken.*;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.ibatis.session.RowBounds;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ShopTest {

    CommonConfig commonConfig = new CommonConfig();



    private RabbitTemplate rabbitTemplate;

    @Resource
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void init() {
        // 在初始化方法中使用 rabbitTemplate
        // ...
        System.out.println("rabbitTemplate = " + rabbitTemplate);
    }

    @Test
    public void imageTest(){
        System.out.println("rabbitTemplate = " + rabbitTemplate);
        rabbitTemplate.convertAndSend("","userInfo"," message");
    }



    private static TradinPostMapper tradinPostMapper;

    @Resource
    public void setChatService(TradinPostMapper tradinPostMapper) {
        ShopTest.tradinPostMapper = tradinPostMapper;
    }



//    @AfterEach
//    void tearDown() throws IOException {
//        this.client.close();
//    }

    // .selectOne(new QueryWrapper<TradinPost>().eq("tradin_id", "test").select("status"))
    @Test
    public void test02(){
        System.out.println(tradinPostMapper);
    }

    @Test
    public void Test01(){
        Claims dataFromToken = TokenUtil.getDataFromToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJ3engiLCJvcmRlcklkQXJyYXkiOlsiYTg5OTdiMjM4MWEiXSwicGF5UG9pbnRTdW0iOjIwOX0.IJRTL1-uqlyk4DkbP5aeesNZj_RdOgUBTNBmDDFnyRo");
        String uid = dataFromToken.get("uid", String.class);
        List<String> list = dataFromToken.get("orderIdArray", ArrayList.class);
        System.out.println(list);
//        BigDecimal payPointSum = BigDecimal.valueOf(Double.parseDouble(dataFromToken.get("payPointSum", String.class)));
        BigDecimal payPointSum = BigDecimal.valueOf(dataFromToken.get("payPointSum", Integer.class));
        System.out.println(payPointSum);
        list.stream().forEach(orderId -> {
            // 校验订单id是否已经存在，保证接口的幂等性，避免重复下单
            System.out.println(orderId);
            // 订单状态、修改时间更新

        });
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

    @Test
    public void test3(){
        JwtBuilder jwtBuilder = Jwts.builder();
        List<ItemOrderDetailDTO> itemOrderDetailDTOS = new ArrayList<>();
////        ItemOrderDetailDTO itemOrderDetailDTO = new ItemOrderDetailDTO("CADKMnRHaE","Vvfmf3xGhw", new BigDecimal(11.2), new BigDecimal(11.2), 2, "蓝色xl", "/images/avatar/174abc6ebd0.png");
////        ItemOrderDetailDTO itemOrderDetailDTO2 = new ItemOrderDetailDTO("biaMlrE5xh","Vvfmf3xGhw", new BigDecimal(11.2), new BigDecimal(11.2), 2, "蓝色xl", "/images/avatar/174abc6ebd0.png");
////        ItemOrderDetailDTO itemOrderDetailDTO3 = new ItemOrderDetailDTO("CADKMnRHaE","Vvfmf3xGhw", new BigDecimal(11.2), new BigDecimal(11.2), 2, "蓝色xl", "/images/avatar/174abc6ebd0.png");
//        itemOrderDetailDTOS.add(itemOrderDetailDTO2);
//        itemOrderDetailDTOS.add(itemOrderDetailDTO);
//        itemOrderDetailDTOS.add(itemOrderDetailDTO3);


        String compact = jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("uid", "wzx")
                .claim("buyerDataId", "4SElECIpJb")
                .claim("itemOrderDetailDTOList", itemOrderDetailDTOS)
                .setExpiration(new Date(System.currentTimeMillis() + Constants.USER_ACTIVE_TIME))
                .signWith(SignatureAlgorithm.HS256, Constants.TOKEN_SECRET)
                .compact();
        System.out.println(compact);
       /* JwtParser jwtParser = Jwts.parser();
        // 通过签名对Token进行解析，得到的结果是一个类似集合的封装类
        Jws<Claims> claimsJws = jwtParser.setSigningKey(Constants.TOKEN_SECRET).parseClaimsJws(compact);
        System.out.println(claimsJws.getBody());*/

    }
}
