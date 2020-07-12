package com.example.sell_master.form;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import java.math.BigDecimal;

/**
 * @zbh
 * @2020/3/2 19:58
 */
@Data
@DynamicInsert
public class ProductForm {
    private  String productId;
    /**名字*/
    private  String productName;
    /**单价*/
    private BigDecimal productPrice;
    /**库存*/
    private  Integer productStock;
    /**描述*/
    private  String productDescription;
    /**小图*/
    private  String  productIcon;

    /**类目编号*/
    private  Integer categoryType;


}
