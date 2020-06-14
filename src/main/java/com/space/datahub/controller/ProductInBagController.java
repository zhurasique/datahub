package com.space.datahub.controller;

import com.space.datahub.domain.ProductInBag;
import com.space.datahub.service.ProductInBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/productinbag")
public class ProductInBagController {

    private final ProductInBagService productInBagService;

    @Autowired
    public ProductInBagController(ProductInBagService productInBagService) {
        this.productInBagService = productInBagService;
    }

    @GetMapping("/bag")
    public List<ProductInBag> byBag(String bag){
        if(bag != null && !bag.isEmpty()) {
            return productInBagService.findByBagName(bag);
        }
        return null;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") ProductInBag productInBag){
        productInBagService.delete(productInBag);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductInBag productInBag){
        productInBagService.save(productInBag);
        return new ResponseEntity<>(productInBag, HttpStatus.OK);
    }
}
