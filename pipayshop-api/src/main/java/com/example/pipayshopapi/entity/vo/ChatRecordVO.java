package com.example.pipayshopapi.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author zxb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRecordVO {


    /**
     * 用户1
     */
    private String userId1;

    /**
     * 用户2
     */
    private String userId2;

    /**
     * 最后发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;


    /**
     * 每条聊天的数据
     */
    private List<ChatVO> chatVOList;

}
