package com.space.datahub.repo;

import com.space.datahub.domain.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInOrderRepo extends JpaRepository<ProductInOrder, Long> {
    List<ProductInOrder> findByOrderNumber(String number);
}
