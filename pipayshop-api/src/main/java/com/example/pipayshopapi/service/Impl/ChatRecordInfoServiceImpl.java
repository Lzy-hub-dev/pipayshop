package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ChatRecordInfo;
import com.example.pipayshopapi.entity.vo.ChatRecordVO;
import com.example.pipayshopapi.entity.vo.ChatVO;
import com.example.pipayshopapi.mapper.ChatRecordInfoMapper;
import com.example.pipayshopapi.service.ChatRecordInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    public boolean saveChatRecord(ChatRecordVO chatRecordVO) {
        return false;
    }


    @Override
    public List<ChatVO> getChatRecord(String userId1, String userId2) {
        return null;

    }
}
