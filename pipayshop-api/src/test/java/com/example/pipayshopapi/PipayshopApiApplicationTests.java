package com.example.pipayshopapi;
import com.example.pipayshopapi.config.QueueConfig;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.BUserInfoService;
import com.example.pipayshopapi.service.ItemOrderInfoService;
import com.example.pipayshopapi.service.ShopCommodityLiveInfoService;
import com.example.pipayshopapi.util.Constants;
import com.example.pipayshopapi.util.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.pipayshopapi.component.WebSocketServer.dailyActiveName;

@SpringBootTest
class PipayshopApiApplicationTests {

    @Resource
    private ItemCommodityInfoMapper itemCommodityInfoMapper;

    @Resource
    private ItemCommodityEvaluateMapper itemCommodityEvaluateMapper;

    @Resource
    private ItemInfoMapper itemInfoMapper;

    @Resource
    private ItemOrderInfoMapper itemOrderInfoMapper;

    @Resource
    private ItemOrderInfoService orderInfoService;

    @Resource
    private ShopOrderInfoMapper shopOrderInfoMapper;

    @Resource
    private ShopCommodityLiveInfoMapper shopCommodityLiveInfoMapper;
    @Resource
    private ShopCommodityLiveInfoService shopCommodityLiveInfoService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private BUserInfoService bUserInfoService;
    @Test
    void contextLoads() {
        System.out.println(bUserInfoService.userWithDraw("wzx", new BigDecimal(1)));
    }
    /***
     * 发送消息
     */
    @Test
    public void sendMessage() throws InterruptedException, IOException {

//        Claims dataFromToken = TokenUtil.getDataFromToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJ3engiLCJiYWxhbmNlIjoiOCIsImV4cCI6Mjg4MDAwMDB9.wqOwmGcIp4bfQQ80fwh7rV9ZuaaxxPrR2VCpHC9lqOY");
       String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJ3engiLCJiYWxhbmNlIjoiOCIsImV4cCI6Mjg4MDAwMDB9.wqOwmGcIp4bfQQ80fwh7rV9ZuaaxxPrR2VCpHC9lqOY";
        JwtParser jwtParser = Jwts.parser();
        // 通过签名对Token进行解析，得到的结果是一个类似集合的封装类
        Jws<Claims> claimsJws = jwtParser.setSigningKey(Constants.TOKEN_SECRET.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        String uid = (String) body.get("uid");
        String balance = (String) body.get("balance");
        System.out.println(uid);
        System.out.println(balance);
    }

}
