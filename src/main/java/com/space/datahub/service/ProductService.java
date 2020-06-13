package com.space.datahub.service;

import com.space.datahub.domain.Product;
import com.space.datahub.repo.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepo productRepository;

    public ProductService(ProductRepo productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findByName(String name){
        return productRepository.findByName(name);
    }

    public List<Product> findByCategoryName(String name){
        return productRepository.findByCategoryName(name);
    }

    public List<Product> findByCategoryId(long id) {
        return productRepository.findByCategoryId(id);
    }

    public void delete(Product product){
        productRepository.delete(product);
    }

    public Product save(@RequestBody Product product){
        return productRepository.save(product);
    }
}
