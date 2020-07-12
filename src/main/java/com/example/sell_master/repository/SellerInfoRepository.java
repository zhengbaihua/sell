package com.example.sell_master.repository;

import com.example.sell_master.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @zbh
 * @2020/3/3 17:42
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String > {
    SellerInfo findByOpenid(String openid);
}
