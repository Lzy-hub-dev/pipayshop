package com.example.pipayshopapi.config;

import com.example.pipayshopapi.service.ItemOrderInfoService;
import com.example.pipayshopapi.service.ShopOrderInfoService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author: jiangjiafeng
 * @ClassName MessageListener
 * @Description TODO
 * @date 2023/8/9 20:05
 * @Version 1.0
 */

//@Component
//@RabbitListener(queues = QueueConfig.QUEUE_MESSAGE)
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
        Message msg1 = (Message) msg;
        byte[] bodyBytes = msg1.getBody();
        String bodyString = new String(bodyBytes);
        String content = bodyString.trim(); // 假设需要移除空格或其他不必要的字符
        String[] split = content.split("_");
        System.out.println(split[0]);
        System.out.println(split[1]);
        if (split[0].equals("item")) {
            itemOrderInfoService.failOrder(split[1]);
        } else if (split[0].equals("shop")) {
            shopOrderInfoService.failOrder(split[1]);
        } else if (split[0].equals("hotel")) {

        }
    }

}