package com.example.sell_master.service;

import com.example.sell_master.dataobject.ProductInfo;
import com.example.sell_master.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品
 */
public interface ProductService {
    //通过id查询
    ProductInfo findOne(String productId);

    /**
     *查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();
    //管理端查询所有在架商品列表，需要分页，参数pageable
    Page<ProductInfo> findAll(Pageable pageable);
    ProductInfo save(ProductInfo productInfo);
    //加库存----取消订单需要加库存，不需要返回类型，直接抛异常
    void increaseStock(List<CartDTO> cartDTOList);
    
    //减库存----下单需要减库存
    void decreaseStock(List<CartDTO> cartDTOList);
    //上架
    ProductInfo onSale(String productId);
    //下架
    ProductInfo offSale(String productId);


}
