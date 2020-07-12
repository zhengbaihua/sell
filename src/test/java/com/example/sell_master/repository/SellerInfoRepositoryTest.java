package com.example.sell_master.repository;

import com.example.sell_master.dataobject.SellerInfo;
import com.example.sell_master.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @zbh
 * @2020/3/3 17:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SellerInfoRepositoryTest {
    @Autowired
    private SellerInfoRepository repository;

    /**
     * 新增
     */
    @Test
    public void save(){
        SellerInfo sellerInfo=new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("123456");
        sellerInfo.setOpenid("abc");

        SellerInfo result=repository.save(sellerInfo);
        Assert.assertNotNull(result);
    }
    @Test
    public void findByOpenid() throws Exception{
        SellerInfo result=repository.findByOpenid("abc");
        Assert.assertEquals("abc",result.getOpenid());

    }

}