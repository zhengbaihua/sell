package com.example.sell_master.exception;

import com.example.sell_master.enums.ResultEnum;
import lombok.Getter;

/**
 * @zbh
 * @2020/2/22 15:45
 */
@Getter
public class SellException extends RuntimeException{
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }

    public SellException( Integer code,String message) {
        super(message);
        this.code = code;
    }
}
