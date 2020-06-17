package com.space.datahub.service;

import com.space.datahub.domain.ProductImage;
import com.space.datahub.repo.ProductImageRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {

    private final ProductImageRepo productImageRepository;

    public ProductImageService(ProductImageRepo productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    public List<ProductImage> findAll(){
        return productImageRepository.findAll();
    }

    public List<ProductImage> findByProductName(String name){
        return productImageRepository.findByProductName(name);
    }

    public ProductImage findByProductId(long id) {
        return productImageRepository.findByProductId(id);
    }

    public void delete(ProductImage productImage){
        productImageRepository.delete(productImage);
    }

    public ProductImage save(ProductImage productImage){
        return productImageRepository.save(productImage);
    }
}
