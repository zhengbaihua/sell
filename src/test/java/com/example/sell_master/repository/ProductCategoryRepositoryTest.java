package com.example.sell_master.repository;

import com.example.sell_master.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCategoryRepositoryTest {

    @Autowired
    private  ProductCategoryRepository repository;

    @Test
    public  void findOne(){
        ProductCategory productCategory=repository.findById(1).orElse(null);
        System.out.println(productCategory.toString());

    }
//    增
    @Test
    @Transactional
    public  void   saveTest(){
        ProductCategory productCategory=new ProductCategory("男生最爱",4);
        ProductCategory result= repository.save(productCategory);
        Assert.assertNotNull(result);
        /*等价于上面
        Assert.assertNotEquals(null,result);*/
    }
//    更新
    @Test
    public  void   updateTest(){
        ProductCategory productCategory=repository.findById(2).orElse(null);
        productCategory.setCategoryType(15);
    }
    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list= Arrays.asList(2,3,4);
        //需要一个无参的构造方法
        List<ProductCategory> result= repository.findByCategoryTypeIn(list);

        Assert.assertNotEquals(0,result.size());
    }


}