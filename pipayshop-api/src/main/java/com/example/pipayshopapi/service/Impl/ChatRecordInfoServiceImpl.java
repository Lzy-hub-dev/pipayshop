package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ChatRecordInfo;
import com.example.pipayshopapi.entity.vo.ChatRecordVO;
import com.example.pipayshopapi.entity.vo.ChatVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.ChatRecordInfoMapper;
import com.example.pipayshopapi.service.ChatRecordInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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
        // 获取两个用户的id
        String userId1 = chatRecordVO.getUserId1();
        String userId2 = chatRecordVO.getUserId2();
        // 坚持数据库中是否有该条数据
        ChatRecordInfo chatRecordInfo = chatRecordInfoMapper.selectOne(new QueryWrapper<ChatRecordInfo>().eq("user_id1", userId1).eq("user_id2", userId2));
        if (chatRecordInfo == null){
            chatRecordInfo = chatRecordInfoMapper.selectOne(new QueryWrapper<ChatRecordInfo>().eq("user_id2", userId1).eq("user_id1", userId2));
        }
        // 如果还是没有就确定该会话的记录已被删除或者为第一次会话
        if (chatRecordInfo == null) {
            // 为第一次会话，创建数据，将聊天记录插入到json中，刷新记录时间
            chatRecordInfo = new ChatRecordInfo(null, userId1, userId2, null, null);
            int insert = chatRecordInfoMapper.insert(chatRecordInfo);
            if (insert < 1){
                throw new BusinessException("保存聊天记录失败");
            }}
        // 获取该条数据的id，方便下方通过主键索引快速定位插入的数据条位置
        Long id = chatRecordInfo.getId();
        // 直接将聊天记录拼接到json中，刷新记录时间
        // 获取内容列表,将ava对象转变为json对象
        List<ChatVO> chatVOList = chatRecordVO.getChatVOList();
        String msgList = chatRecordInfo.getMsgList();
        String jsonString;
        if (msgList == null || "".equals(msgList)){
            // 表中数据就直接把接收到的数据存进去
            jsonString = JSON.toJSONString(chatVOList);
        }else {
            // 获取原有的聊天数据
            List<ChatVO> chatRecordInfos = JSONObject.parseArray(JSON.parseArray(msgList).toJSONString(), ChatVO.class);
            // 拼接新数据
            chatRecordInfos.addAll(chatVOList);
            jsonString = JSON.toJSONString(chatRecordInfos);
        }
        ChatRecordInfo chatRecordInfo1 = new ChatRecordInfo(null, null, null, new Date(), jsonString);
        int update = chatRecordInfoMapper.update(chatRecordInfo1, new QueryWrapper<ChatRecordInfo>().eq("id", id));
        return update > 0;
    }


    @Override
    public List<ChatVO> getChatRecord(String userId1, String userId2) {
        // 坚持数据库中是否有该条数据
        ChatRecordInfo chatRecordInfo = chatRecordInfoMapper.selectOne(new QueryWrapper<ChatRecordInfo>().eq("user_id1", userId1).eq("user_id2", userId2));
        if (chatRecordInfo == null){
            chatRecordInfo = chatRecordInfoMapper.selectOne(new QueryWrapper<ChatRecordInfo>().eq("user_id2", userId1).eq("user_id1", userId2));
        }
        if (chatRecordInfo == null || chatRecordInfo.getMsgList() == null){
            return null;
        }
        return JSONObject.parseArray(JSON.parseArray(chatRecordInfo.getMsgList()).toJSONString(), ChatVO.class);
    }
}
