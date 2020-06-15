package com.space.datahub.controller;

import com.space.datahub.domain.Product;
import com.space.datahub.domain.ProductImage;
import com.space.datahub.service.ProductImageService;
import com.space.datahub.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/productimage")
public class ProductImageController {

    @Value("${upload.path}")
    private String uploadPath;

    private final ProductImageService productImageService;
    private final ProductService productService;


    public ProductImageController(ProductImageService productImageService, ProductService productService) {
        this.productImageService = productImageService;
        this.productService = productService;
    }

    @GetMapping
    public List<ProductImage> byBag(){
        return productImageService.findAll();
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

    @PostMapping()
    public ResponseEntity<?> addImg(@RequestParam long product, @RequestParam MultipartFile image) throws IOException {

        ProductImage productImage = new ProductImage();
        Optional<Product> optional = productService.findById(product);
        optional.ifPresent(productImage::setProduct);

        if(image != null){
            File uploadDir = new File(uploadPath);

            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String resultFileName = UUID.randomUUID().toString() + "." + "." + image.getOriginalFilename();
            image.transferTo(new File(uploadPath + "/" + resultFileName));

            productImage.setImage(resultFileName);
        }

        productImageService.save(productImage);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
