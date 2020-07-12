package com.example.sell_master.utils;

import com.example.sell_master.VO.ResultVO;

/**
 * @zbh
 * @2020/2/21 21:09
 */
public class ResultVOUtil {
    public static ResultVO success(Object object){
        ResultVO resultVO=new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }
    //什么都不弹
    public static ResultVO success(){
        return success(null);
    }
    //失败,当发送错误时，调用这个erro方法
    public static ResultVO erro(Integer code,String msg){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
    //
    public static ResultVO error(Integer code,String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(message);
        resultVO.setCode(code);
        return  resultVO;
    }

}
