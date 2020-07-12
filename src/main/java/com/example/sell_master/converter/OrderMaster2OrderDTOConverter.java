package com.example.sell_master.converter;

import com.example.sell_master.dataobject.OrderMaster;
import com.example.sell_master.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @zbh
 * @2020/2/22 20:41
 */
public class OrderMaster2OrderDTOConverter {
    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }
    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e ->
                convert(e)
                ).collect(Collectors.toList());
    }
}
