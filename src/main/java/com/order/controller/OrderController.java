package com.order.controller;

import com.common.modal.Product;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<Product> getOrders(){
        log.info("OrderService: inside getOrder before called inventory");
        ResponseEntity<Product[]> inventoryResponse=restTemplate.getForEntity("http://localhost:8082/inventory/",Product[].class);
        log.info("OrderService: inside getOrder after called inventory");
        return List.of(inventoryResponse.getBody());
    }

    @PostMapping("/create")
    public Product createOrders(@RequestBody Product product){
        log.info("OrderService: inside createOrder before called inventory");
        Product createProdctResponse=restTemplate.postForObject("http://localhost:8082/inventory/create",product,Product.class);
        log.info("OrderService: inside createOrder after called inventory");
        return createProdctResponse;
    }

    public List<Product> getFallBackOrders(Throwable t){
        return List.of(new Product());
    }

}
