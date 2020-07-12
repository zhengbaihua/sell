package com.example.sell_master.controller;

import com.example.sell_master.config.ProjectUrlConfig;
import com.example.sell_master.constant.CookieConstant;
import com.example.sell_master.constant.RedisConstant;
import com.example.sell_master.dataobject.SellerInfo;
import com.example.sell_master.enums.ResultEnum;
import com.example.sell_master.service.SellerService;
import com.example.sell_master.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**卖家用户操作
 * @zbh
 * @2020/3/4 19:29
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;
//    private StringRedisTemplate redisTemplate;
    /**
     * 登录
     * @param openid
     * @param map
     */
    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String,Object> map){
        //1.openid去和数据库里的数据匹配
        SellerInfo sellerInfo=sellerService.findSellerInfoByopenid(openid);
        if(sellerInfo==null){
            map.put("msg", ResultEnum.LODIN_FAIL.getMessage());
            //if login失败，跳转到订单列表页
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error");
        }
        //2.设置token至redis
        String token= UUID.randomUUID().toString();
        //设置过期时间
        Integer expire= RedisConstant.EXPIRE;
        //格式化----希望存储的token是以下划线开头的一个key
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),
                openid,expire, TimeUnit.SECONDS);
//        redisTemplate.opsForValue().set("abc","zzzz");
        //3.设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);
        //地址要用绝对地址
          return new ModelAndView("redirect:"+projectUrlConfig.getSell()+"/sell/seller/order/list");
    }

    /**
     * 登出
     * @param request
     * @param response
     * @param map
     * @return
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String,Object> map){
        //1.从cookie里查询
        Cookie cookie=CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie!=null){
            //2.清除redis
            redisTemplate.opsForValue().getOperations().delete
                    (String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //3.清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }
}
