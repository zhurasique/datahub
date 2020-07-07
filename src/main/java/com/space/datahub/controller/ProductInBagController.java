package com.space.datahub.controller;

import com.space.datahub.domain.ProductInBag;
import com.space.datahub.service.ProductInBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/productinbag")
public class ProductInBagController {

    private final ProductInBagService productInBagService;

    @Autowired
    public ProductInBagController(ProductInBagService productInBagService) {
        this.productInBagService = productInBagService;
    }

    @GetMapping
    public List<ProductInBag> list(){
        return productInBagService.findAll();
    }

    @GetMapping("/bag")
    public List<ProductInBag> byBag(String bag){
        return productInBagService.findByBagName(bag);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") ProductInBag productInBag){
        productInBagService.delete(productInBag);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam String product, @RequestParam String bag){
        return new ResponseEntity<>(productInBagService.save(product, bag), HttpStatus.OK);
    }
}
