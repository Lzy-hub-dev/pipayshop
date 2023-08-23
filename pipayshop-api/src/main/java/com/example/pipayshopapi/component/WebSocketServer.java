package com.example.pipayshopapi.component;


import cn.hutool.core.collection.ConcurrentHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author websocket服务
 */

@Component
@ServerEndpoint(value = "/dailyActive/{userId}")
public class WebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    // 注入查看聊天列表的服务

    /**
     * 记录当前在线连接数
     */

   public static final   ConcurrentHashSet<String> dailyActiveCount = new ConcurrentHashSet<>();



    public static final String dailyActiveName="dailyActiveName";


    private static StringRedisTemplate stringRedisTemplate;

    @Autowired
    public void setChatService(StringRedisTemplate stringRedisTemplate) {
        WebSocketServer.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        log.error(userId+"----------------------------------------------进入");
        // 保存当前用户session
        dailyActiveCount.add(userId);
        // 存入redis中去
        stringRedisTemplate.opsForValue().set(dailyActiveName,String.valueOf(dailyActiveCount.size()));
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        log.error(userId+"----------------------------------------------关闭");
        // 移除当前用户session
        dailyActiveCount.remove(userId);
        // 存入redis中去
        stringRedisTemplate.opsForValue().set(dailyActiveName,String.valueOf(dailyActiveCount.size()));
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