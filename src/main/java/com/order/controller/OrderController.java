package com.order.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    private Logger log = LoggerFactory.getLogger(OrderController.class);
    RestTemplate restTemplate=new RestTemplate();

    @GetMapping("/")
    @CircuitBreaker(name="circuitbreaker",fallbackMethod = "getFallBackOrders")
    public String getOrders(){
        log.info("OrderService: inside getOrder before called inventory");
        String inventoryResponse=restTemplate.getForObject("http://localhost:8082/inventory/",String.class);
        log.info("OrderService: inside getOrder after called inventory");
        return inventoryResponse +" from order service";
    }

    @PostMapping("/create")
    public String createOrders(){
        log.info("OrderService: inside createOrder before called inventory");
        String createOrderResponse=restTemplate.postForObject("http://localhost:8082/inventory/create","This order for mobiles",String.class);
        log.info("OrderService: inside createOrder after called inventory");
        return createOrderResponse;
    }

    public String getFallBackOrders(Throwable t){
        return "This is your orders from fall back methods";
    }

}
