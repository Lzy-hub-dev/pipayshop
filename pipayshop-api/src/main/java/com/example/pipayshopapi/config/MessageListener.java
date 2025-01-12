package com.example.pipayshopapi.config;

import com.example.pipayshopapi.service.ItemOrderInfoService;
import com.example.pipayshopapi.service.ShopOrderInfoService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
@RabbitListener(queues = QueueConfig.QUEUE_MESSAGE)
public class MessageListener {


    @Resource
    private ItemOrderInfoService itemOrderInfoService;
    @Resource
    private ShopOrderInfoService shopOrderInfoService;

    /***
     * 监听消息
     * @param msg
     */
    @RabbitHandler
    public void msg(@Payload Object msg) {
        Map<Object, Object> objectObjectMap = Collections.synchronizedMap(new HashMap<>());
        ConcurrentHashMap<Object, Object> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();
        Message msg1 = (Message) msg;
        byte[] bodyBytes = msg1.getBody();
        String bodyString = new String(bodyBytes);
        // 假设需要移除空格或其他不必要的字符
        String content = bodyString.trim();
        String[] split = content.split("_");
        System.out.println(split[0]);
        System.out.println(split[1]);
        if (split[0].equals("item")) {
            itemOrderInfoService.failOrder(split[1]);
        } else if (split[0].equals("shop")) {
            shopOrderInfoService.failOrder(split[1]);
        } else if (split[0].equals("hotel")) {

        }else if (split[0].equals("shopLive")){
            shopOrderInfoService.failLiveOrder(split[1]);
        }
    }

}
