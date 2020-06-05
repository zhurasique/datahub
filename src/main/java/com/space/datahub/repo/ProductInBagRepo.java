package com.space.datahub.repo;

import com.space.datahub.domain.ProductInBag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInBagRepo extends JpaRepository<ProductInBag, Long> {
    List<ProductInBag> findByBagName(String name);
}
