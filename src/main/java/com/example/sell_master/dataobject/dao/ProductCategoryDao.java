package com.example.sell_master.dataobject.dao;

import com.example.sell_master.dataobject.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @zbh
 * @2020/3/19 21:24
 */
public class ProductCategoryDao {
    @Autowired
    ProductCategoryMapper mapper;
    public int insertByMap(Map<String ,Object> map){
        return mapper.insertByMap(map);
    }
}
