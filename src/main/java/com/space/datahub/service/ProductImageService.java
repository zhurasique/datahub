package com.space.datahub.service;

import com.space.datahub.domain.ProductImage;
import com.space.datahub.repo.ProductImageRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductImageService {

    private final ProductImageRepo productImageRepository;

    public ProductImageService(ProductImageRepo productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    public List<ProductImage> findAll(){
        return productImageRepository.findAll();
    }

    public List<ProductImage> findByProductName(String name){
        return productImageRepository.findByProductName(name);
    }

    public List<ProductImage> findByProductId(long id) {
        List<ProductImage> tmp = findAll();
        List<ProductImage> productImages = new ArrayList<>();
        for(int i = 0; i < tmp.size(); i++){
            if(tmp.get(i).getProduct().getId() == id)
                productImages.add(tmp.get(i));
        }
        return productImages;
    }

    public void delete(ProductImage productImage){
        productImageRepository.delete(productImage);
    }

    public ProductImage save(ProductImage productImage){
        return productImageRepository.save(productImage);
    }
}
