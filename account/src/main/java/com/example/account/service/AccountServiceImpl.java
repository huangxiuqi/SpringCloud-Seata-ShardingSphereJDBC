package com.example.account.service;

import com.example.account.dao.AccountDAO;
import com.example.common.service.AccountService;
import io.seata.core.context.RootContext;
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
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    @Transactional
    public void debit(int userId, int money) {
        log.info("全局事务id ：" + RootContext.getXID());
        accountDAO.insert(userId, -money);
        if (userId % 2 == 0) {
            throw new RuntimeException("test roll back");
        }
    }
}
