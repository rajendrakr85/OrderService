package com.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    RestTemplate restTemplate=new RestTemplate();

    @GetMapping("/")
    public String getOrders(){
        String inventoryResponse=restTemplate.getForObject("http://localhost:8082/inventory/",String.class);
        return inventoryResponse +" from order service";
    }

    @PostMapping("/create")
    public String createOrders(){
        String createOrderResponse=restTemplate.postForObject("http://localhost:8082/inventory/create","This order for mobiles",String.class);
        return createOrderResponse;
    }
}
