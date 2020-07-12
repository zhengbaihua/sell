package com.example.sell_master.dataobject.mapper;

import com.example.sell_master.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @zbh
 * @2020/3/6 16:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ProductCategoryMapperTest {
    @Autowired
    private ProductCategoryMapper mapper;
    @Test
    public void insertByMap() throws Exception{
        Map<String ,Object> map=new HashMap<>();
        map.put("category_name","师傅最爱");
        map.put("category_type",11);
        int result = mapper.insertByMap(map);
        Assert.assertEquals(1,result);

    }
    @Test
    public void insertByObject(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("师妹最爱");
        productCategory.setCategoryType(12);
        int result = mapper.insertByObject(productCategory);
        Assert.assertEquals(1,result);

    }
    @Test
    public  void findbyCategoryType(){
        ProductCategory result = mapper.findbyCategoryType(12);
        Assert.assertNotNull(result);
    }

    @Test
    public void findbyCategoryName(){
        List<ProductCategory> result = mapper.findbyCategoryName("师傅最爱");
        Assert.assertNotEquals(0,result.size());
    }
    @Test
    public void updateByCategoryType(){
        int result = mapper.updateByCategoryType("师傅最爱的分类",12);
        Assert.assertEquals(1,result);
    }
    @Test
    public void updateByObjct(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("师妹最爱");
        productCategory.setCategoryType(12);
        int result = mapper.updateByObjct(productCategory);
        Assert.assertEquals(1,result);
    }
    @Test
    public void deleteByCategoryType(){
        int result = mapper.deleteByCategoryType(12);
        Assert.assertEquals(1,result);
    }

    @Test
    public void selectByCategoryType(){
        ProductCategory productCategory = mapper.selectByCategoryType(10);
        Assert.assertNotNull(productCategory);
    }

}