package com.example.storage.service;

import com.example.common.service.StorageService;
import com.example.storage.dao.StorageDAO;
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
public class StorageServiceImpl implements StorageService {

    private static final Logger log = LoggerFactory.getLogger(StorageServiceImpl.class);

    private final StorageDAO storageDAO;

    public StorageServiceImpl(StorageDAO storageDAO) {
        this.storageDAO = storageDAO;
    }

    @Override
    @Transactional
    public void deduct(String commodityCode, int count) {
        log.info("全局事务id ：" + RootContext.getXID());
        storageDAO.insert(commodityCode, count);
        if ("7654322".equals(commodityCode)) {
            throw new RuntimeException("test roll back");
        }
    }
}
