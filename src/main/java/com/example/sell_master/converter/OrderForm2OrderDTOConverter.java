package com.example.sell_master.converter;

import com.example.sell_master.dataobject.OrderDetail;
import com.example.sell_master.dto.OrderDTO;
import com.example.sell_master.enums.ResultEnum;
import com.example.sell_master.exception.SellException;
import com.example.sell_master.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @zbh
 * @2020/2/22 23:11
 */
@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm){
        Gson gson=new Gson();

        OrderDTO orderDTO=new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        //购物车
        List<OrderDetail> orderDetailList=new ArrayList<>();
        try {
            orderDetailList=gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e){
            log.error("【对象转换】 错误,string={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
//        BeanUtils.copyProperties; 因为字段名不一样，所以不能copy
        return orderDTO;
    }
}
