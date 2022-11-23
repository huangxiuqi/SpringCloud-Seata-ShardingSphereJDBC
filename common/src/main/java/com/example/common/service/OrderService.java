package com.example.common.service;


import com.example.common.entity.OrderEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huangxiuqi
 */
@FeignClient("order-service")
public interface OrderService {

    /**
     * 创建订单
     */
    @RequestMapping("/create")
    OrderEntity create(@RequestParam("userId") int userId,
                       @RequestParam("commodityCode") String commodityCode,
                       @RequestParam("orderCount") int orderCount);
}
