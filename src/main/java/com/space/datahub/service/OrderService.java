package com.space.datahub.service;

import com.space.datahub.domain.Order;
import com.space.datahub.repo.OrderRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepo orderRepository;
    private final UserService userService;

    public OrderService(OrderRepo orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public List<Order> findByUserUsername(String name){
        return orderRepository.findByUserUsername(name);
    }

    public List<Order> findByStatus(int status){
        return orderRepository.findByStatus(status);
    }

    public Order findByNumber(String number){
        return orderRepository.findByNumber(number);
    }

    public Order findById(String id){
        long parsedId = Long.parseLong(id);
        Optional<Order> optional = orderRepository.findById(parsedId);
        List<Order> order = new ArrayList<>();
        optional.ifPresent(opt -> {
            order.add(opt);
        });
        return order.get(0);
    }

    public Order update(@PathVariable("id") Order orderFromDb, @RequestBody Order order){
        BeanUtils.copyProperties(order, orderFromDb, "id");
        return changeOrderId(orderFromDb);
    }

    public Order changeOrderId(@RequestBody Order order){
        return orderRepository.save(order);
    }

    public Order save(String username,
                      String phone,
                      String address,
                      String city,
                      String pcode){
        Order order = new Order();
        order.setUser(userService.findByUsername(username));
        order.setPhone(phone);
        order.setAddress(address);
        order.setCity(city);
        order.setPcode(pcode);

        order.setStatus(0);
        order.setNumber(UUID.randomUUID().toString() + order.getPcode());

        orderRepository.save(order);
        return order;
    }
}
