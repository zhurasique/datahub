package com.space.datahub.repo;

import com.space.datahub.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByUserUsername(String name);
    List<Order> findByStatus(int status);
    Order findByNumber(String number);
}
