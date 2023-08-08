package com.example.pipayshopapi.entity.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ThinkPad
 */

@Getter
@AllArgsConstructor
public enum PaymentEnum {
    PAYMENT_ENUM_1(1, "订单不存在","失败"),
    PAYMENT_ENUM_2(2,"订单不是待支付状态","失败"),
    PAYMENT_ENUM_3(3,"支付金额少于订单金额","失败"),
    PAYMENT_ENUM_4(4,"调用太快","失败"),
    PAYMENT_ENUM_5(5,"余额不足,请前往充值","失败"),
    PAYMENT_ENUM_6(6,"支付成功","成功"),
    PAYMENT_ENUM_7(7,"处理成功","成功"),
    PAYMENT_ENUM_8(8,"处理失败","失败");
    private final Integer code;
    private final String msg;
    private final String status;

    public static String getMsgByCode(Integer code) {
        for (PaymentEnum value : PaymentEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getMsg();
            }
        }
        return null;
    }

    public static String getStatusByCode(Integer code) {
        for (PaymentEnum value : PaymentEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getStatus() ;
            }
        }
        return null;
    }


}
