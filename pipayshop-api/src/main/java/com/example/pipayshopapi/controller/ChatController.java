package com.example.pipayshopapi.controller;

import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ChatRecordInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

//    /**
//     * 添加数据
//     */
//    @PostMapping ("saveChatRecord")
//    @ApiOperation("保存聊天记录")
//    public ResponseVO<String> saveChatRecord(@RequestBody ChatRecordVO chatRecordVO) {
//        try {
//
//
//            return ResponseVO.getSuccessResponseVo("保存聊天记录成功");
//        } catch (Exception e) {
//            log.error("保存聊天记录失败,报错如下：{}", e.getMessage());
//            throw new BusinessException("保存聊天记录失败"+e.getLocalizedMessage()+e.toString()+e.getCause().toString());
//        }
//    }
//
//    /**
//     * 获取聊天数据
//     */
//    @GetMapping ("getChatRecord/{userId1}/{userId2}")
//    @ApiOperation("获取聊天记录")
//    public ResponseVO<List<ChatVO>> getChatRecord(@PathVariable String userId1, @PathVariable String userId2) {
//        try {
//
//            return ResponseVO.getSuccessResponseVo(chatVOList);
//        } catch (Exception e) {
//            log.error("获取聊天记录失败,报错如下：{}", e.getMessage());
//            throw new BusinessException("获取聊天记录失败"+e.getLocalizedMessage()+e +e.getCause().toString());
//        }
//    }
}
