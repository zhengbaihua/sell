package com.example.sell_master.aspect;

import com.example.sell_master.constant.CookieConstant;
import com.example.sell_master.constant.RedisConstant;
import com.example.sell_master.exception.SellAuthorizeException;
import com.example.sell_master.exception.SellException;
import com.example.sell_master.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 卖家授权
 * @zbh
 * @2020/3/5 18:56
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;
    //验证
    @Pointcut("execution(public * com.example.sell_master.controller.Seller*.*(..))"+
    "&& !execution(public * com.example.sell_master.controller.SellerUserController.*(..))")
    public void verify(){

    };
    //方法的具体实现
    @Before("verify()")
    public void doverify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        //查询coolie
        Cookie cookie= CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie==null){
            log.warn("【登录校验】 Cookie中查不到token");
            throw new SellAuthorizeException();
        }
        //去redis里查询
        String tokenValue=redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,
                cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)){
            log.warn("【登录校验】 Redis里面查不到token");
            throw new SellAuthorizeException();
        }


    }
}
