package com.space.datahub.controller;

import com.space.datahub.domain.ProductInBag;
import com.space.datahub.service.BagService;
import com.space.datahub.service.ProductInBagService;
import com.space.datahub.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/productinbag")
public class ProductInBagController {

    private final ProductInBagService productInBagService;
    private final ProductService productService;
    private final BagService bagService;

    @Autowired
    public ProductInBagController(ProductInBagService productInBagService, ProductService productService, BagService bagService) {
        this.productInBagService = productInBagService;
        this.productService = productService;
        this.bagService = bagService;
    }

    @GetMapping
    public List<ProductInBag> list(){
        return productInBagService.findAll();
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
    public ResponseEntity<?> create(@RequestParam String product, @RequestParam String bag){
        ProductInBag productInBag = new ProductInBag();
        productInBag.setBag(bagService.findByName(bag));

        long parsedId = Long.parseLong(product);
        productInBag.setProduct(productService.findById(parsedId));
        productInBagService.save(productInBag);
        return new ResponseEntity<>(productInBag, HttpStatus.OK);
    }
}
