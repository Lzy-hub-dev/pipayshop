package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * fileName: ShopUserFollowInfoVO
 * author: 四面神
 * createTime:2023/8/2 12:07
 * 描述:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopUserFollowInfoVO {

    /**
     * 用户id
     */
    private String uid;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userImage;

    /**
     * 关注时间
     */
    private LocalDateTime createTime;


}
