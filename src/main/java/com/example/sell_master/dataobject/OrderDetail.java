package com.example.sell_master.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * @zbh
 * @2020/2/22 12:35
 */
@Entity
@Data
@DynamicUpdate

public class OrderDetail{
    @Id
    private String detailId;
    /**订单id*/
    private String orderId;
    /**商品id*/
    private String productId;
    /**商品名称*/
    private String productName;
    /**单价*/
    private BigDecimal productPrice;
    /**商品数量*/
    private int productQuantity;
    /**商品小图*/
    private String productIcon;

}
