package com.example.sell_master.dto;

import lombok.Data;

/**
 * 购物车
 * @zbh
 * @2020/2/22 16:37
 */
@Data
public class CartDTO {
    /**商品id*/
    private  String productId;
    /**数量*/
    private  Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
