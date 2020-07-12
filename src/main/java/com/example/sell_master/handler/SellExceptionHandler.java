package com.example.sell_master.handler;

import com.example.sell_master.VO.ResultVO;
import com.example.sell_master.config.ProjectUrlConfig;
import com.example.sell_master.exception.ResponseBankException;
import com.example.sell_master.exception.SellAuthorizeException;
import com.example.sell_master.exception.SellException;
import com.example.sell_master.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 对异常进行捕获
 * @zbh
 * @2020/3/5 19:32
 */
@ControllerAdvice
public class SellExceptionHandler {
    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    //拦截登陆异常
    //http://sell.natapp4.cc/sell/wechat/qrAuthorize?returnUrl=http://zbh123.natapp1.cc/sell/seller/login
    @ExceptionHandler(value = SellAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getWechatOpenAuthorize())
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(projectUrlConfig.getSell())
                .concat("/sell/seller/login"));
    }
    /*@ExceptionHandler(value = SellAuthorizeException.class)
    public ModelAndView handlerAuthorizeExcrption() {
        return new ModelAndView( "localhost:8080/sell/seller/order/list");
//        return new ModelAndView("redirect:" + "sell.com/sell/wechat/qrAuthorize/?returnurl=/sell/seller/login");
    }*/

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellerException(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleResponseBankException(){

    }

}
