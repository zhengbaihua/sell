package com.example.sell_master.service;

import com.example.sell_master.dataobject.ProductCategory;

import java.util.List;

/**
 * 类目
 */
public interface CategoryService {
    ProductCategory findOne(Integer categoryId);
    List<ProductCategory> findAll();
    //
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    /*新增和更新都是save*/
    ProductCategory save(ProductCategory productCategory);

}
