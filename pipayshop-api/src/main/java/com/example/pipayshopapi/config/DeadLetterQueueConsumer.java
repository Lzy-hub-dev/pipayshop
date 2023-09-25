package com.example.pipayshopapi.config;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pipayshopapi.entity.TradinOrder;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.TradinOrderMapper;
import com.example.pipayshopapi.service.TradinOrderService;
import com.example.pipayshopapi.util.Constants;
import com.example.pipayshopapi.util.TokenUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class DeadLetterQueueConsumer {

    @Resource
    private TradinOrderMapper tradinOrderMapper;
    @Resource
    private TradinOrderService tradinOrderService;

    @RabbitListener(queues = Constants.DELAYED_QUEUE_NAME)
    public void receiveDelayedQueue(Message message){
        String orderMap = new String(message.getBody());
        Map map = (Map) JSON.parse(orderMap);
        String userId = (String) map.get("userId");
        String orderId = (String) map.get("orderId");
        TradinOrder tradinOrder = tradinOrderMapper.selectOne(new QueryWrapper<TradinOrder>()
                                                            .eq("order_id", orderId));
        if (tradinOrder.getStatus() == 2){
            String tradinOrderToken = TokenUtil.getTradinOrderToken(orderId, userId);
            boolean confirm = tradinOrderService.confirmTransaction(tradinOrderToken);
            if (! confirm){
                throw new BusinessException("发生错误") ;
            }
        }
    }
}