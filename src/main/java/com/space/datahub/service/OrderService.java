package com.space.datahub.service;

import com.space.datahub.domain.Order;
import com.space.datahub.repo.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepo orderRepository;

    public OrderService(OrderRepo orderRepository) {
        this.orderRepository = orderRepository;
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

    public Order findById(long id){
        Optional<Order> optional = orderRepository.findById(id);
        List<Order> order = new ArrayList<>();
        optional.ifPresent(opt -> {
            order.add(opt);
        });
        return order.get(0);
    }

    public Order save(@RequestBody Order order){
        return orderRepository.save(order);
    }
}
