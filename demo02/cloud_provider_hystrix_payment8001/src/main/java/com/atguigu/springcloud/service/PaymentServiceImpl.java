package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池："+Thread.currentThread().getName()+"paymentInfo_OK,id:"+id+"\t"+"哈哈";
    }

    @Override
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandle",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000") //超时设置
    })
    public String paymentInfo_Timeout(Integer id) {
        int timenumber =3;
        try {
            TimeUnit.SECONDS.sleep(timenumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池："+Thread.currentThread().getName()+"paymentInfo_Timeout,id:"+id+"\t"+"哈哈 耗时:"+timenumber+"s";
    }

    @Override
    @HystrixCommand(fallbackMethod = "paymentCircuitHandle",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value ="true" ),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")
    })
    public String paymentCircuit(Integer id) {
        if (id<0){
            throw new RuntimeException("id 不能为负数!");
        }
        String serialNumber = IdUtil.simpleUUID();
        return "线程池："+Thread.currentThread().getName()+"paymentCircuit.serialNumber:"+serialNumber+"\t"+"ok";
    }

    public String paymentInfo_TimeoutHandle(Integer id) {
        return "线程池："+Thread.currentThread().getName()+"paymentInfo_TimeoutHandle,id:"+id+"\t"+"fallback";
    }
    public String paymentCircuitHandle(Integer id){
        return "paymentCircuitHandle circuitBreak!";
    }

}
