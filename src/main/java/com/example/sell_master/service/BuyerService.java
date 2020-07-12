package com.example.sell_master.service;

import com.example.sell_master.dto.OrderDTO;

/**
 * 买家
 * @zbh
 * @2020/2/23 15:11
 */
public interface BuyerService {
    //查询一个订单
    OrderDTO findOrderOne(String openid,String orderId);
    //取消订单
    OrderDTO cancelOrder(String openid,String orderId);
}
