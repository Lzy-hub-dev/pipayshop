package com.example.pipayshopapi.controller;

import com.example.pipayshopapi.entity.ChatRecordInfo;
import com.example.pipayshopapi.entity.vo.ChatVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ChatRecordInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wzx
 */
@RestController
@RequestMapping("chat/msg")
@Api(value = "聊天室处理接口", tags = "聊天室处理接口")
@Slf4j
public class ChatController {

    @Resource
    ChatRecordInfoService chatRecordInfoService;

    /**
     * 定期轮询删除聊天记录
     */
    @PostMapping ("deleteChatRecord")
    @ApiOperation("定期轮询删除聊天记录")
    public ResponseVO<String> deleteChatRecord() {
        try {
            chatRecordInfoService.deleteChatRecord();
            return ResponseVO.getSuccessResponseVo("删除聊天记录成功");
        } catch (Exception e) {
            log.error("删除聊天记录失败,报错如下：{}", e.getMessage());
            throw new BusinessException("删除聊天记录失败");
        }
    }

    /**
     * 添加数据
     */
    @PostMapping ("saveChatRecord")
    @ApiOperation("保存聊天记录")
    public ResponseVO<String> saveChatRecord(@RequestBody List<ChatRecordInfo> chatRecordInfoList) {
        try {
            boolean flag = chatRecordInfoService.saveChatRecord(chatRecordInfoList);
            if (!flag) {
                throw new RuntimeException();
            }
            return ResponseVO.getSuccessResponseVo("保存聊天记录成功");
        } catch (Exception e) {
            log.error("保存聊天记录失败,报错如下：{}", e.getMessage());
            throw new BusinessException("保存聊天记录失败"+e.getLocalizedMessage()+e+e.getCause().toString());
        }
    }

    /**
     * 获取聊天数据(分页 + 按时间倒序排列)
     */
    @GetMapping("getChatRecord/{senderId}/{receiverId}")
    @ApiOperation("获取聊天数据(分页 + 按时间倒序排列)")
    public ResponseVO<List<ChatVO>> getChatRecord(@PathVariable String senderId, @PathVariable String receiverId) {
        try {
            List<ChatVO> list = chatRecordInfoService.getChatRecord(senderId, receiverId);
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            log.error("获取聊天记录失败,报错如下：{}", e.getMessage());
            throw new BusinessException("获取聊天记录失败"+e.getLocalizedMessage()+e +e.getCause().toString());
        }
    }
}
