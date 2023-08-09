package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

/**
 * @author:
 * @ClassName ChangePriceDTO
 * @Description
 * @date 2023/8/9 14:04
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePriceDTO {
    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 价格
     */
    private BigDecimal price;
}
