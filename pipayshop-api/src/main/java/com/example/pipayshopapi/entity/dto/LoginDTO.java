package com.example.pipayshopapi.entity.dto;

import lombok.Data;

@Data
public class LoginDTO {

    private String userId;
    private String userName;
    private String accessToken;
}
