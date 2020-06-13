package com.space.datahub.controller;

import com.space.datahub.domain.Product;
import com.space.datahub.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<Product> list(){
        return productService.findAll();
    }

    @GetMapping("/name")
    public Product byName(@RequestParam String name){
        if(name != null && !name.isEmpty()) {
            return productService.findByName(name);
        }
        return null;
    }

    @GetMapping("/category/name")
    public List<Product> byType(@RequestParam String category){
        if(category != null && !category.isEmpty())
            return productService.findByCategoryName(category);
        return null;
    }

    @GetMapping("/category/id")
    public List<Product> byId(@RequestParam long id){
        return productService.findByCategoryId(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Product product){
        productService.delete(product);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product){
        productService.save(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
