package com.space.datahub.controller;

import com.space.datahub.domain.ProductInOrder;
import com.space.datahub.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/productinorder")
public class ProductInOrderController {

    private final ProductInOrderService productInOrderService;

    @Autowired
    public ProductInOrderController(ProductInOrderService productInOrderService) {
        this.productInOrderService = productInOrderService;
    }

    @GetMapping
    public List<ProductInOrder> list(){
        return productInOrderService.findAll();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") ProductInOrder productInOrder){
        productInOrderService.delete(productInOrder);
    }

    @GetMapping("/order")
    public List<ProductInOrder> orders(@RequestParam String order){
        return productInOrderService.findByOrderNumber(order);
    }

    @GetMapping("/user")
    public List<ProductInOrder> user(@RequestParam String user){
        return productInOrderService.user(user);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam String bag, @RequestParam int length, @RequestParam String number){
        return new ResponseEntity<>(productInOrderService.save(bag, length, number), HttpStatus.OK);
    }
}
