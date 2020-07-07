package com.space.datahub.controller;

import com.space.datahub.domain.Product;
import com.space.datahub.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/id")
    public Product byProductId(@RequestParam String id){
        return productService.findById(id);
    }

    @GetMapping("/name")
    public List<Product> byName(@RequestParam String name){
        return productService.findByName(name);
    }

    @GetMapping("/department/name")
    public List<Product> byTypeD(@RequestParam String department){
        return productService.byTypeD(department);
    }

    @GetMapping("/type/name")
    public List<Product> byTypeS(@RequestParam String type){
        return productService.byTypeS(type);
    }

    @GetMapping("/category/name")
    public List<Product> byType(@RequestParam String category){
        return productService.findByCategoryName(category);
    }

    @GetMapping("/category/id")
    public List<Product> byId(@RequestParam long id){
        return productService.findByCategoryId(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam String name, @RequestParam double price, @RequestParam String description, @RequestParam String category) {
        return new ResponseEntity<>(productService.save(name, price, description, category), HttpStatus.OK);
    }
}
