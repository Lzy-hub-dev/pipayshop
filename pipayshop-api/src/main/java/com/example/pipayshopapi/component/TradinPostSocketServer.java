package com.example.pipayshopapi.component;


import cn.hutool.core.collection.ConcurrentHashSet;
import com.example.pipayshopapi.service.TradinPostService;
import com.example.pipayshopapi.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


/**
 * @author websocket服务
 */

@Component
@ServerEndpoint(value = "/TradinPostSocket/{tradinId}")
public class TradinPostSocketServer {

    private static final Logger log = LoggerFactory.getLogger(TradinPostSocketServer.class);

    private static TradinPostService tradinPostService;

    @Autowired
    public void setTradinPostService(TradinPostService tradinPostService) {
        TradinPostSocketServer.tradinPostService = tradinPostService;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("tradinId") String tradinId) {
        log.error(tradinId+"----------------------------------------------进入");
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("tradinId") String tradinId) {
        log.error(tradinId+"----------------------------------------------关闭");
        // 改变对应的tradinId的状态
        tradinPostService.updateStatusByTradinId(tradinId);
    }


    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     */
    @OnMessage
    public void onMessage(Session session, String message,@PathParam("userId") String userId) {



    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

}