package com.space.datahub.controller;

import com.space.datahub.domain.ProductImage;
import com.space.datahub.service.ProductImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/productimage")
public class ProductImageController {

    private final ProductImageService productImageService;

    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping
    public List<ProductImage> list(){
        return productImageService.findAll();
    }

    @GetMapping("/slider")
    public List<ProductImage> slider(@RequestParam List<Long> list){
        return productImageService.slider(list);
    }

    @GetMapping("/unique")
    public List<ProductImage> unique(){
        return productImageService.unique();
    }

    @GetMapping("/product")
    public List<ProductImage> byProductName(String name){
        return productImageService.findByProductName(name);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") ProductImage productImage){
        productImageService.delete(productImage);
    }

    @PostMapping
    public ResponseEntity<?> addImg(@RequestParam long product, @RequestParam("image") List<MultipartFile> image) throws IOException {
        return new ResponseEntity<>(productImageService.save(product, image), HttpStatus.OK);
    }
}
