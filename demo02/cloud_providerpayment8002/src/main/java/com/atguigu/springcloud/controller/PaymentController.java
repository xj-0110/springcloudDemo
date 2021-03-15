package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/add")
    public CommonResult add(@RequestBody Payment payment){
        int result =paymentService.add(payment);
        log.info("插入结果 ="+result);
        if (result>0){
            return new CommonResult(200,"插入数据库成功 端口号 =" +serverPort,result);
        }else {
            return new CommonResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping(value = "/get/{id}")
    public CommonResult get(@PathVariable("id")Long id){
        Payment payment =paymentService.getPaymentById(id);
        log.info("插入结果 ="+payment);
        if (payment!=null){
            return new CommonResult(200,"查询成功",payment);
        }else {
            return new CommonResult(444,"没有查询记录！ 查询ID ="+id,null);
        }
    }

    @GetMapping("/lb")
    public String paymentLB(){
        return serverPort;
    }
}
