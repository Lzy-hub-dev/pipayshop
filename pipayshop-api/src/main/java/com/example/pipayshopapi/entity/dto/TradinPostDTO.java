package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradinPostDTO {
    private String tradinId;
    private String traderUid;
    private Integer typeId;
    private String piAddress;

}
