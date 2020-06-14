package com.space.datahub.service;

import com.space.datahub.domain.ProductInBag;
import com.space.datahub.repo.ProductInBagRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProductInBagService {
    private final ProductInBagRepo productInBagRepo;

    public ProductInBagService(ProductInBagRepo productInBagRepo) {
        this.productInBagRepo =  productInBagRepo;
    }

    public List<ProductInBag> findByBagName(String name){
        return productInBagRepo.findByBagName(name);
    }

    public void delete(ProductInBag productInBag){
        productInBagRepo.delete(productInBag);
    }

    public ProductInBag save(@RequestBody ProductInBag productInBag){
        return productInBagRepo.save(productInBag);
    }
}
