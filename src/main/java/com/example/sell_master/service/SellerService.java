package com.example.sell_master.service;

import com.example.sell_master.dataobject.SellerInfo;

/**
 * 卖家端
 * @zbh
 * @2020/3/3 17:57
 */
public interface SellerService {
    /**
     * 通过openid查询卖家端信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByopenid(String openid);
}
