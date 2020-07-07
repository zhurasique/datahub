package com.space.datahub.controller;

import com.space.datahub.domain.Order;
import com.space.datahub.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> list(){
        return orderService.findAll();
    }

    @GetMapping("/id")
    public Order byId(@RequestParam String id){
        return orderService.findById(id);
    }

    @GetMapping("/user")
    public List<Order> user(@RequestParam String user){
        return orderService.findByUserUsername(user);
    }

    @PutMapping("{id}")
    public Order update(@PathVariable("id") Order orderFromDb, @RequestBody Order order){
        return orderService.update(orderFromDb, order);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam String username,
                                    @RequestParam String phone,
                                    @RequestParam String address,
                                    @RequestParam String city,
                                    @RequestParam String pcode){
        return new ResponseEntity<>(orderService.save(username, phone, address, city, pcode), HttpStatus.OK);
    }
}
