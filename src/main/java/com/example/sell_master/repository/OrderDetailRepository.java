package com.example.sell_master.repository;

import com.example.sell_master.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @zbh
 * @2020/2/22 13:11
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String > {
    List<OrderDetail> findByOrderId(String orderId);
}
