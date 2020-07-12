package com.example.sell_master.VO;

import lombok.Data;

/**
 * http请求返回的最外层对象
 * @zbh
 * @2020/2/20 23:23
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
//data定义为一个泛型
public class ResultVO<T> {
    /**
     * code 为错误码 code为0表示成功
     */
    private  Integer code;
    /**提示信息 */
//    private  String msg="";    将msg设置为非必须字段的返回，就设置一个初始值
    private  String msg;
    /**data是返回的具体内容*/
    private T data;
}
