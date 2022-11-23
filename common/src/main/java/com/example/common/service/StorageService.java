package com.example.common.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huangxiuqi
 */
@FeignClient("storage-service")
public interface StorageService {

    /**
     * 扣除存储数量
     */
    @RequestMapping("/deduct")
    void deduct(@RequestParam("commodityCode") String commodityCode, @RequestParam("count") int count);
}
