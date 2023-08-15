package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FansVO {
    private String itemId;
    private String followId;
    private Date createTime;
    private String userName;
    private String userImage;
}
