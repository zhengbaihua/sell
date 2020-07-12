package com.example.sell_master.repository;

import com.example.sell_master.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @zbh
 * @2020/2/22 13:00
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    /**按照买家openid来查，并且有分页*/
    Page<OrderMaster> findByBuyerOpenid(String  buyerOpenid, Pageable pageable);
    OrderMaster findByOrderId(String orderId);

}
