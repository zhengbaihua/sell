package com.example.sell_master.utils;

import org.apache.http.HttpResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * coolie工具类
 * @zbh
 * @2020/3/5 1:14
 */
public class CookieUtil {
    /**
     * 设置
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void  set(HttpServletResponse response,
                            String name,
                            String value,
                            int maxAge){
        Cookie cookie=new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);

    }

    /**
     * 获取cookie
     * @param request
     * @param name
     * @return
     */
    public static Cookie  get(HttpServletRequest request,
                            String name){
        Map<String,Cookie> cookieMap=readCookieMap(request);
        //判断是否有这个cookie的name,
        if (cookieMap.containsKey(name)){
            //如果包含，就获取
            return cookieMap.get(name);
        }else {
            return null;
        }
    }

    /**
     * 将cookie封装成Map
     * @param request
     * @return
     */
    private static Map<String ,Cookie> readCookieMap(HttpServletRequest request){
        Map<String ,Cookie> cookieMap=new HashMap<>();
        Cookie[] cookies=request.getCookies();
        //判断coolie是否有值,
        if (cookies!=null){
            //如果不等于null，开始遍历
            for (Cookie cookie:cookies){
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }
}
