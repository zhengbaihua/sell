package com.example.sell_master.constant;

/**
 * Redis常量
 * @zbh
 * @2020/3/5 0:48
 */
public interface RedisConstant {
    //设置过期时间
    String TOKEN_PREFIX="token_%s";
    Integer EXPIRE=7200;//2小时----过期时间

}
