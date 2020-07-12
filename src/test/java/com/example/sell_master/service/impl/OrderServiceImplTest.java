package com.example.sell_master.service.impl;

import com.example.sell_master.dataobject.OrderDetail;
import com.example.sell_master.dto.OrderDTO;
import com.example.sell_master.enums.OrderStatusEnum;
import com.example.sell_master.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @zbh
 * @2020/2/22 17:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;
    private final String BUYER_OPENID="10110";
    private final String ORDER_ID="1582372488802645652";

    @Test
    public void create() throws Exception{
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName("嫦娥");
        orderDTO.setBuyerAddress("王者峡谷");
        orderDTO.setBuyerPhone("1887995202");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        //购物车
        List<OrderDetail> orderDetailList=new ArrayList<>();

        OrderDetail o1=new OrderDetail();
        o1.setProductId("1234568");
        o1.setProductQuantity(1);
        orderDetailList.add(o1);

        OrderDetail o2=new OrderDetail();
        o2.setProductId("123457");
        o2.setProductQuantity(20);
        orderDetailList.add(o2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result= orderService.create(orderDTO);
        log.info("【创建订单】result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() throws Exception{
        OrderDTO result= orderService.findOne(ORDER_ID);
        log.info("【创建单个订单】result={}",result);
        Assert.assertEquals(ORDER_ID,result.getOrderId());
    }

    @Test
    public void findList() throws Exception{
        //查询订单列表
        PageRequest request=PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage=orderService.findList(BUYER_OPENID,request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() throws Exception{
        OrderDTO orderDTO= orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() throws Exception{
        OrderDTO orderDTO= orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() throws Exception{
        OrderDTO orderDTO= orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCSESS.getCode(),result.getPayStatus());
    }
    @Test
    public void list(){
        PageRequest request=PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage=orderService.findList(request);
//        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
        Assert.assertTrue("查询所有的订单列表",orderDTOPage.getTotalElements()>0);
    }
}