package com.example.business.service;

/**
 * @author huangxiuqi
 */
public interface BusinessService {

    /**
     * 采购
     */
    void purchase(int userId, String commodityCode, int orderCount);
}
