package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ThinkPad
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IncompleteDTO {

    private String identifier;


    private TransactionDTO transaction;
}
