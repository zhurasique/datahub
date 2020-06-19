package com.space.datahub.service;

import com.space.datahub.domain.ProductInOrder;
import com.space.datahub.repo.ProductInOrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProductInOrderService {
    private final ProductInOrderRepo productInOrderRepository;

    public ProductInOrderService(ProductInOrderRepo productInOrderRepository) {
        this.productInOrderRepository = productInOrderRepository;
    }

    public List<ProductInOrder> findByOrderNumber(String number){
        return productInOrderRepository.findByOrderNumber(number);
    }

    public List<ProductInOrder> findAll(){
        return productInOrderRepository.findAll();
    }

    public void delete(ProductInOrder productInOrder){
        productInOrderRepository.delete(productInOrder);
    }

    public ProductInOrder save(@RequestBody ProductInOrder productInOrder){
        return productInOrderRepository.save(productInOrder);
    }
}
