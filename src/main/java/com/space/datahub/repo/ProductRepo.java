package com.space.datahub.repo;

import com.space.datahub.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);
    List<Product> findById(long id);
    List<Product> findByType(String type);
}
