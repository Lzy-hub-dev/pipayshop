package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ChatRecordInfo;
import com.example.pipayshopapi.entity.vo.ChatDataVO;
import com.example.pipayshopapi.entity.vo.ChatListVO;
import com.example.pipayshopapi.entity.vo.ChatVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
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
                .filter(Objects::nonNull)
                .peek(chatRecordInfo -> {
                    chatRecordInfoMapper.insert(chatRecordInfo);
                })
                .count();
        return count >= chatRecordInfoList.size();
    }


    @Override
    public PageDataVO getChatRecord(ChatDataVO chatDataVO) {
        // 满足分页的page格式
        chatDataVO.setPage((chatDataVO.getPage() - 1) * chatDataVO.getLimit());
        List<ChatVO> chatRecord = chatRecordInfoMapper.getChatRecord(chatDataVO);
        Integer count = chatRecordInfoMapper.getChatRecordSum(chatDataVO);
        return new PageDataVO(count, chatRecord);
    }

    @Override
    public List<ChatListVO> getChatRecordAsUser(String userId) {
        return chatRecordInfoMapper.getChatRecordAsUser(userId);
    }

    @Override
    public List<ChatListVO> getChatRecordAsItem(String itemId) {
        return chatRecordInfoMapper.getChatRecordAsItem(itemId);
    }
}
