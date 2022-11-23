package com.example.business.service;

import com.example.common.service.OrderService;
import com.example.common.service.StorageService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author huangxiuqi
 */
@Service
public class BusinessServiceImpl implements BusinessService {

    private static final Logger log = LoggerFactory.getLogger(BusinessServiceImpl.class);

    private final StorageService storageService;

    private final OrderService orderService;

    public BusinessServiceImpl(StorageService storageService, OrderService orderService) {
        this.storageService = storageService;
        this.orderService = orderService;
    }

    @Override
    @GlobalTransactional
    public void purchase(int userId, String commodityCode, int orderCount) {
        log.info("全局事务id ：" + RootContext.getXID());
        orderService.create(userId, commodityCode, orderCount);
        storageService.deduct(commodityCode, orderCount);
    }
}
