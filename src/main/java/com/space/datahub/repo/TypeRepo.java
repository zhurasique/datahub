package com.space.datahub.repo;

import com.space.datahub.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepo extends JpaRepository<Product, Long> {
}
