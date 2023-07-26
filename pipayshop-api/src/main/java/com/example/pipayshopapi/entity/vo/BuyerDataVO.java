package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mongdie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerDataVO {

    /**
     * 收货人的名字
     */
    private String userName;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 收货人的手机号
     */
    private String phone;
}
