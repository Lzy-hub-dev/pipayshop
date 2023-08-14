package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ChatRecordInfo;
import com.example.pipayshopapi.entity.vo.ChatVO;
import com.example.pipayshopapi.mapper.ChatRecordInfoMapper;
import com.example.pipayshopapi.service.ChatRecordInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wzx
 * @since 2023-07-18
 */
@Service
public class ChatRecordInfoServiceImpl extends ServiceImpl<ChatRecordInfoMapper, ChatRecordInfo> implements ChatRecordInfoService {

    @Resource
    ChatRecordInfoMapper chatRecordInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteChatRecord() {
        chatRecordInfoMapper.deleteOutTime();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveChatRecord(List<ChatRecordInfo> chatRecordInfoList) {
        // 校验数据正确性
        if (chatRecordInfoList == null || chatRecordInfoList.size() == 0) {
            return false;
        }
        // 插入
        long count = chatRecordInfoList.stream()
                .parallel()
                .filter(Objects::nonNull)
                .peek(chatRecordInfo -> {
                    chatRecordInfoMapper.insert(chatRecordInfo);
                })
                .count();
        return count >= chatRecordInfoList.size();
    }


    @Override
    public List<ChatVO> getChatRecord(String senderId, String receiverId) {
        return chatRecordInfoMapper.getChatRecord(senderId, receiverId);
    }
}
