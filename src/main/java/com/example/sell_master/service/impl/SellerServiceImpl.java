package com.example.sell_master.service.impl;

import com.example.sell_master.dataobject.SellerInfo;
import com.example.sell_master.repository.SellerInfoRepository;
import com.example.sell_master.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @zbh
 * @2020/3/3 17:59
 */
@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByopenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
