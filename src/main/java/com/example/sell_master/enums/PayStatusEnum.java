package com.example.sell_master.enums;

import lombok.Getter;

/**
 * @zbh
 * @2020/2/22 0:12
 */
@Getter
public enum PayStatusEnum implements CodeEnum {
    WAIT(0,"等待支付"),
    SUCCSESS(1,"支付成功")
    ;
    private  Integer code;
    private  String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
