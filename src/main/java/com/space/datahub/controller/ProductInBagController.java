package com.space.datahub.controller;

import com.space.datahub.domain.ProductInBag;
import com.space.datahub.repo.ProductInBagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/productinbag")
public class ProductInBagController {
    private final ProductInBagRepo productInBagRepository;


    @Autowired
    public ProductInBagController(ProductInBagRepo productInBagRepository) {
        this.productInBagRepository = productInBagRepository;
    }

    @GetMapping("/filter/bag")
    public Iterable<ProductInBag> byBag(String bag){
        Iterable<ProductInBag> productList = null;
        if(bag != null && !bag.isEmpty()) {
            productList = productInBagRepository.findByBagName(bag);
            return productList;
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductInBag productInBag){
        productInBagRepository.save(productInBag);
        return new ResponseEntity<>(productInBag, HttpStatus.OK);
    }
}
