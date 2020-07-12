package com.example.sell_master.utils;

import com.example.sell_master.enums.CodeEnum;

/**
 * @zbh
 * @2020/3/1 20:21
 */
public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for (T each: enumClass.getEnumConstants()){
            if (code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
