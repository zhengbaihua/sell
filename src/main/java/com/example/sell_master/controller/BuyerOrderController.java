package com.example.sell_master.controller;

import com.example.sell_master.VO.ResultVO;
import com.example.sell_master.converter.OrderForm2OrderDTOConverter;
import com.example.sell_master.dto.OrderDTO;
import com.example.sell_master.enums.ResultEnum;
import com.example.sell_master.exception.SellException;
import com.example.sell_master.form.OrderForm;
import com.example.sell_master.service.BuyerService;
import com.example.sell_master.service.OrderService;
import com.example.sell_master.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @zbh
 * @2020/2/22 22:44
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;
    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String ,String >> create(@Valid OrderForm orderForm,
                                                 BindingResult bindingResult){
        //判断表单校验后是否有错误
        if (bindingResult.hasErrors()){
            log.error("【创建订单】 参数不正确,orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage());
        }
        //如果参数正确
        /**
         * 把OrderForm转换成OrderDTO
         */
        //对象转换----把OrderForm转换成OrderDTO
        OrderDTO orderDTO= OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】 购物车不能为空");
            throw new SellException(ResultEnum.CART_ENTY);
        }
        //创建订单
        OrderDTO createResult=orderService.create(orderDTO);
        Map<String ,String > map=new HashMap<>();
        map.put("orderId",createResult.getOrderId());

        return ResultVOUtil.success(map);
    }
    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】 openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request=PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage=orderService.findList(openid,request);

        //转换Date->Long

        return ResultVOUtil.success(orderDTOPage.getContent());
//        return ResultVOUtil.success();
      /*  //为String类型的处理
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(0);
        return resultVO;*/
    }
    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
//        // TODO 不安全的做法,改进
//        OrderDTO orderDTO = orderService.findOne(orderId);
//          if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
//
//          }

        OrderDTO orderDTO=buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel (@RequestParam("openid") String openid,
                            @RequestParam("orderId") String orderId){

        buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success();
    }
}
