package com.example.sell_master.service;

import com.example.sell_master.dto.OrderDTO;

/**
 * 推送消息
 * @zbh
 * @2020/3/5 23:36
 */
public interface PushMessageService {
    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
