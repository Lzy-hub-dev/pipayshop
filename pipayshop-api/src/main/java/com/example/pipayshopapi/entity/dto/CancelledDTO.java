package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ThinkPad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelledDTO {
    private String paymentId;
    // 网店订单id
    private String itemOrderId;
    // 实体店订单id
    private String shopOrderId;


}
