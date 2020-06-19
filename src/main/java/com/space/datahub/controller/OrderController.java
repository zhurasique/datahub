package com.space.datahub.controller;

import com.space.datahub.domain.Order;
import com.space.datahub.service.OrderService;
import com.space.datahub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public List<Order> list(){
        return orderService.findAll();
    }

    @GetMapping("/id")
    public Order byId(@RequestParam String id){
        long parsedId = Long.parseLong(id);
        return orderService.findById(parsedId);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam String username,
                                    @RequestParam String phone,
                                    @RequestParam String address,
                                    @RequestParam String city,
                                    @RequestParam String pcode){
        Order order = new Order();
        order.setUser(userService.findByUsername(username));
        order.setPhone(phone);
        order.setAddress(address);
        order.setCity(city);
        order.setPcode(pcode);

        order.setStatus(0);
        order.setNumber(UUID.randomUUID().toString() + order.getId());

        orderService.save(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
