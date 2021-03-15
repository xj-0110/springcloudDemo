package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallback implements PaymentHystrixService {

    public String paymentInfo_OK(Integer id) {
        return "paymentInfo_OK     fallback";
    }

    public String paymentInfo_Timeout(Integer id) {
        return "paymentInfo_Timeout     fallback";
    }
}
