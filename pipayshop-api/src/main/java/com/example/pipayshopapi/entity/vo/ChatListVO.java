package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wzx
 * 用户聊天列表展示
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatListVO {

    /**
     * 聊天对方的id，这里可以存放的是itemId，也可以是userId
     */
    private String chatObjectId;

    /**
     * 聊天对方的名字，这里可以存放的是itemName，也可以是userName
     */
    private String chatObjectName;

    /**
     * 聊天对方的头像
     */
    private String userImage;

    /**
     * 最新的消息
     */
    private String newMessage;

    /**
     * 最新消息的发送时间
     */
    private String newMessageTime;
}
