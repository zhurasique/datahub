package com.space.datahub.repo;

import com.space.datahub.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepo extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductName(String name);
    ProductImage findByProductId(long id);
}
