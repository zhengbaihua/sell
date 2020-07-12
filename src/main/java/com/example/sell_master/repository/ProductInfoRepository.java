package com.example.sell_master.repository;

import com.example.sell_master.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    //查询上架商品，通过商品状态
    List<ProductInfo> findByProductStatus(Integer productStatus);

}
