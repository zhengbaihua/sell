package com.example.sell_master.repository;

import com.example.sell_master.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository repository;
    @Test
    //新增
    public void saveTest(){
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("103456");
        productInfo.setProductName("瘦肉粥");
        productInfo.setProductPrice(new BigDecimal(5.5));
        productInfo.setProductStock(50);
        productInfo.setProductDescription("很好喝的粥");
        productInfo.setProductIcon("http://xxxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);

        ProductInfo result= repository.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    //查询上架商品
    public void  findByProductStatus() throws  Exception{
        List<ProductInfo> productInfoList= repository.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfoList.size());

    }

}