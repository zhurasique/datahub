package com.space.datahub.controller;

import com.space.datahub.domain.ProductImage;
import com.space.datahub.service.ProductImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/productimage")
public class ProductImageController {

    @Value("${upload.path}")
    private String uploadPath;

    private final ProductImageService productImageService;


    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping("/product")
    public List<ProductImage> byBag(String name){
        if(name != null && !name.isEmpty()) {
            return productImageService.findByProductName(name);
        }
        return null;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") ProductImage productImage){
        productImageService.delete(productImage);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductImage productImage){

        productImageService.save(productImage);
        return new ResponseEntity<>(productImage, HttpStatus.OK);
    }
}
