package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ChatRecordInfo;
import com.example.pipayshopapi.entity.vo.ChatRecordVO;
import com.example.pipayshopapi.entity.vo.ChatVO;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wzx
 * @since 2023-07-18
 */
public interface ChatRecordInfoService extends IService<ChatRecordInfo> {

    /**
     * 定期轮询删除聊天记录
     */
    void deleteChatRecord();

    /**
     * 保存聊天记录
     */
    boolean saveChatRecord(ChatRecordVO chatRecordVO);

    /**
     * 获取聊天记录
     */
    List<ChatVO> getChatRecord(String userId1, String userId2);
}


