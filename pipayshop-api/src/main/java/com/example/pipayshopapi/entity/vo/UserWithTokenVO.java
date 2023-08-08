package com.example.pipayshopapi.entity.vo;

import com.example.pipayshopapi.entity.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wzx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithTokenVO {

    private UserInfo userInfo;

    private String token;

}
