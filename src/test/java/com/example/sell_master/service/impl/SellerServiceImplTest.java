package com.example.sell_master.service.impl;

import com.example.sell_master.dataobject.SellerInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @zbh
 * @2020/3/3 18:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class SellerServiceImplTest {
    private static final String openid="abc";
    @Autowired
    private SellerServiceImpl sellerService;

    @Test
    public void findSellerInfoByopenid() throws Exception{
        SellerInfo result=sellerService.findSellerInfoByopenid(openid);
        Assert.assertEquals(openid,result.getOpenid());
    }
}