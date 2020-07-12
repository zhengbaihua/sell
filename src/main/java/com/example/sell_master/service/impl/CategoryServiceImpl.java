package com.example.sell_master.service.impl;

import com.example.sell_master.dataobject.ProductCategory;
import com.example.sell_master.dataobject.mapper.ProductCategoryMapper;
import com.example.sell_master.repository.ProductCategoryRepository;
import com.example.sell_master.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 类目
 */
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    /*public CategoryServiceImpl() {
        super();
    }*/
    @Autowired
    private ProductCategoryRepository repository;

    /*@Autowired
    private ProductCategoryMapper mapper;*/

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findById(categoryId).orElse(null);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
