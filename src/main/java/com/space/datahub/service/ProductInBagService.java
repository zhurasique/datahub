package com.space.datahub.service;

import com.space.datahub.domain.ProductInBag;
import com.space.datahub.repo.ProductInBagRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInBagService {

    private final ProductInBagRepo productInBagRepository;
    private final ProductService productService;
    private final BagService bagService;

    public ProductInBagService(ProductInBagRepo productInBagRepository, ProductService productService, BagService bagService) {
        this.productInBagRepository =  productInBagRepository;
        this.productService = productService;
        this.bagService = bagService;
    }

    public List<ProductInBag> findByBagName(String bag){
        if(bag != null && !bag.isEmpty()) {
            return productInBagRepository.findByBagName(bag);
        }
        return null;
    }

    public List<ProductInBag> findAll(){
        return productInBagRepository.findAll();
    }

    public void delete(ProductInBag productInBag){
        productInBagRepository.delete(productInBag);
    }

    public ProductInBag save(String product, String bag){
        ProductInBag productInBag = new ProductInBag();
        productInBag.setBag(bagService.findByName(bag));

        long parsedId = Long.parseLong(product);
        productInBag.setProduct(productService.findById(parsedId));
        productInBagRepository.save(productInBag);

        return productInBag;
    }
}
