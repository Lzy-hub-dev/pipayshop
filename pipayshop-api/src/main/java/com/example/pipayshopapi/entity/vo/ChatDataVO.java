package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wzx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDataVO {

    private String senderId;

    private String receiverId;

    private Integer page;

    private Integer limit;
}
