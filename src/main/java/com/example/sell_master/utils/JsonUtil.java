package com.example.sell_master.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @zbh
 * @2020/3/1 13:21
 */
public class JsonUtil {
    public static String toJson(Object object){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson=gsonBuilder.create();
        return gson.toJson(object);
    }
}
