package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wzx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

    private String uid;

    private String password;

    private String piName;
}
