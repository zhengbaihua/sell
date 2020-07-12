package com.example.sell_master.service;

import com.example.sell_master.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

/**
 * 支付
 * @zbh
 * @2020/3/1 10:16
 */
public interface PayService {
    PayResponse create(OrderDTO orderDTO);
    PayResponse notify(String notifyData);
    //退款
    RefundResponse refund(OrderDTO orderDTO);
}
