package com.example.sell_master.service.impl;

import com.example.sell_master.dto.OrderDTO;
import com.example.sell_master.service.OrderService;
import com.example.sell_master.service.PushMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @zbh
 * @2020/3/6 0:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PushMessageServiceImplTest {
    @Autowired
    private PushMessageServiceImpl pushMessageService;
    @Autowired
    private OrderService orderService;
     @Test
   public void orderStatus() throws Exception{
        OrderDTO orderDTO =orderService.findOne("1582372488802645652");
        pushMessageService.orderStatus(orderDTO);

    }
}