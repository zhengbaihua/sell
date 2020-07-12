package com.example.sell_master.repository;

import com.example.sell_master.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @zbh
 * @2020/2/22 14:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderDetailRepositoryTest {
    @Autowired
    private  OrderDetailRepository repository;
    @Test
    public void saveTest(){
        OrderDetail orderDetail=new OrderDetail() ;
        orderDetail.setDetailId("1234567810");
        orderDetail.setOrderId("1112");
        orderDetail.setProductIcon("http://xxx.jpg");
        orderDetail.setProductId("1111222");
        orderDetail.setProductName("瘦肉粥");
        orderDetail.setProductPrice(new BigDecimal(5));
        orderDetail.setProductQuantity(3);
        OrderDetail result=repository.save(orderDetail);
        Assert.assertNotNull(result);
    }
    @Test
    public void findByOrderId() throws Exception{
        List<OrderDetail> orderDetailList =repository.findByOrderId("1112");
        Assert.assertNotEquals(0,orderDetailList.size());
    }

}