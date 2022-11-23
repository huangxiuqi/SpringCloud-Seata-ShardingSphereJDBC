package com.example.order.service;

import com.example.common.entity.OrderEntity;
import com.example.common.service.AccountService;
import com.example.common.service.OrderService;
import com.example.order.dao.OrderDAO;
import io.seata.core.context.RootContext;
import org.apache.shardingsphere.transaction.annotation.ShardingSphereTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangxiuqi
 */
@Service
@RestController
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderDAO orderDao;

    private final AccountService accountService;

    public OrderServiceImpl(OrderDAO orderDao, AccountService accountService) {
        this.orderDao = orderDao;
        this.accountService = accountService;
    }

    @Override
    @Transactional
    @ShardingSphereTransactionType(TransactionType.BASE)
    public OrderEntity create(int userId, String commodityCode, int orderCount) {
        log.info("全局事务id ：" + RootContext.getXID());
        int orderMoney = 1000;

        OrderEntity order = new OrderEntity();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(orderCount);
        order.setMoney(orderMoney);
        order = orderDao.insert(order);

        accountService.debit(userId, orderMoney);

        return order;
    }
}
