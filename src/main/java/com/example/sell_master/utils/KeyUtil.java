package com.example.sell_master.utils;

import java.util.Random;

/**
 * 生成随机数的一个方法
 * @zbh
 * @2020/2/22 16:09
 */
public class KeyUtil {
    /**
     * 生产唯一的主键
     * 格式：时间+随机数
     * @return
     */
    //synchronized关键字，处理多线程
    public static synchronized String genUniqueKey(){
        Random random=new Random();
        System.currentTimeMillis();
        //生成6位随机数
        Integer number= random.nextInt(900000)+100000;
        //精确到毫秒
        return System.currentTimeMillis()+String.valueOf(number);


    }
}
