package com.example.business.controller;

import com.example.business.service.BusinessService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangxiuqi
 */
@RestController
@RequestMapping("/business")
public class BusinessController {

    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @RequestMapping("/purchase")
    public String purchase(int userId, String commodityCode, int orderCount) {
        businessService.purchase(userId, commodityCode, orderCount);
        return "success";
    }
}
