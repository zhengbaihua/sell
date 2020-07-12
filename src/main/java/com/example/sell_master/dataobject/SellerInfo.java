package com.example.sell_master.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @zbh
 * @2020/3/3 17:39
 */
@Data
@Entity
public class SellerInfo {
    @Id
    private String sellerId;
    private String username;
    private String password;
    private String openid;


}
