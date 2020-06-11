package com.space.datahub.controller;

import com.space.datahub.domain.Product;
import com.space.datahub.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {
    private final ProductRepo productRepository;

    @Autowired
    public ProductController(ProductRepo productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> list(){
        return productRepository.findAll();
    }

    @GetMapping("/name")
    public Iterable<Product> byName(@RequestParam String name){
        Iterable<Product> products = null;
        if(name != null && !name.isEmpty()) {
            products = productRepository.findByName(name);
            return products;
        }
        return null;
    }

    @GetMapping("/category/name")
    public Iterable<Product> byType(@RequestParam String category){
        Iterable<Product> products = null;
        if(category != null && !category.isEmpty()) {
            products = productRepository.findByCategoryName(category);
            return products;
        }
        return null;
    }

    @GetMapping("/category/id")
    public Iterable<Product> byId(@RequestParam long id){
        Iterable<Product> products = null;
        products = productRepository.findByCategoryId(id);
        return products;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Product product){
        productRepository.delete(product);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product){
        productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
