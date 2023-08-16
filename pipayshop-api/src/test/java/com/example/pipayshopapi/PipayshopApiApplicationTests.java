package com.example.pipayshopapi;
import com.example.pipayshopapi.config.QueueConfig;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.ItemOrderInfoService;
import com.example.pipayshopapi.service.ShopCommodityLiveInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    @Test
    void contextLoads() {

    }
    /***
     * 发送消息
     */
    @Test
    public void sendMessage() throws InterruptedException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("发送当前时间:"+dateFormat.format(new Date()));
        Map<String,String> message = new HashMap<>();
        message.put("name","szitheima");
        rabbitTemplate.convertAndSend(QueueConfig.QUEUE_MESSAGE_DELAY, message, message1 -> {
            message1.getMessageProperties().setExpiration(1000*60*10+"");
            return message1;
        });
         System.in.read();
    }

}
