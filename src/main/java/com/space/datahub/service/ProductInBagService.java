package com.space.datahub.service;

import com.space.datahub.domain.ProductInBag;
import com.space.datahub.repo.ProductInBagRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProductInBagService {
    private final ProductInBagRepo productInBagRepository;

    public ProductInBagService(ProductInBagRepo productInBagRepository) {
        this.productInBagRepository =  productInBagRepository;
    }

    public List<ProductInBag> findByBagName(String name){
        return productInBagRepository.findByBagName(name);
    }

    public List<ProductInBag> findAll(){
        return productInBagRepository.findAll();
    }

    public void delete(ProductInBag productInBag){
        productInBagRepository.delete(productInBag);
    }

    public ProductInBag save(@RequestBody ProductInBag productInBag){
        return productInBagRepository.save(productInBag);
    }
}
