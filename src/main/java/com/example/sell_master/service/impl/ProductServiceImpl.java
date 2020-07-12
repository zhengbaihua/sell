package com.example.sell_master.service.impl;

import com.example.sell_master.dataobject.ProductInfo;
import com.example.sell_master.dto.CartDTO;
import com.example.sell_master.enums.ProductStatusEnum;
import com.example.sell_master.enums.ResultEnum;
import com.example.sell_master.exception.SellException;
import com.example.sell_master.repository.ProductInfoRepository;
import com.example.sell_master.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findById(productId).orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }


    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo=repository.findById(cartDTO.getProductId()).orElse(null);
            if (productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            //增加库存
            Integer result=productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);

        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
           ProductInfo productInfo= repository.findById(cartDTO.getProductId()).orElse(null);
           if (productInfo==null){
               throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
           }
           //减库存
           Integer result= productInfo.getProductStock()-cartDTO.getProductQuantity();
           //判断减之后的结果
           if (result<0){
               throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
           }
           productInfo.setProductStock(result);
           repository.save(productInfo);

        }

    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo=repository.findById(productId).orElse(null);
        if (productInfo==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if (productInfo.getProductStatusEnum()==ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //如果都正确，更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo=repository.findById(productId).orElse(null);
        if (productInfo==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if (productInfo.getProductStatusEnum()==ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //如果都正确，更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }
}
