package com.example.sell_master.repository;

import com.example.sell_master.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @zbh
 * @2020/2/22 13:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository repository;
    private final String OPENID="10110";
    @Test
    public void saveTest(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("123458");
        orderMaster.setBuyerName("小乔");
        orderMaster.setBuyerPhone("136525523");
        orderMaster.setBuyerAddress("江东");
        orderMaster.setBuyerOpenid(OPENID);
        /**订单金额*/
        orderMaster.setOrderAmount(new BigDecimal(50));
        OrderMaster result= repository.save(orderMaster);
        Assert.assertNotNull(result);
    }
    @Test
    public void findByBuyerOpenid() throws Exception{
        PageRequest request= PageRequest.of(1,3);
        Page<OrderMaster> result= repository.findByBuyerOpenid(OPENID,request);
        Assert.assertNotEquals(0,result.getTotalElements());
    }

}