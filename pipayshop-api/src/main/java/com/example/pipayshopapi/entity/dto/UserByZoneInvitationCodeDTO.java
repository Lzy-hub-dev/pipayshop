package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserByZoneInvitationCodeDTO {
    //当前用户id
    private String userId;
    //邀请码
    private String invitationCode;
}
