package com.example.common.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huangxiuqi
 */
@FeignClient("account-service")
public interface AccountService {

    /**
     * 从用户账户中借出
     */
    @RequestMapping("/debit")
    void debit(@RequestParam("userId") int userId, @RequestParam("money") int money);
}
