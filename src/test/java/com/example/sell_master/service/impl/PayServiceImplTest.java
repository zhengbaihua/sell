package com.example.sell_master.service.impl;

import com.example.sell_master.dto.OrderDTO;
import com.example.sell_master.service.OrderService;
import com.example.sell_master.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @zbh
 * @2020/3/1 10:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class PayServiceImplTest {
    @Autowired
    private PayService payService;
    @Autowired
    private OrderService orderService;
    @Test
    public void create() throws Exception{
        OrderDTO orderDTO = orderService.findOne("1585384409910738775");

        payService.create(orderDTO );
    }
    @Test
    public void refund(){
        OrderDTO orderDTO=orderService.findOne("1585384698576501937");
        payService.refund(orderDTO);

    }
}