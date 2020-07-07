package com.space.datahub.service;

import com.space.datahub.domain.ProductImage;
import com.space.datahub.repo.ProductImageRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductImageService {

    @Value("${upload.path}")
    private String uploadPath;

    private final ProductImageRepo productImageRepository;
    private final ProductService productService;

    public ProductImageService(ProductImageRepo productImageRepository, ProductService productService) {
        this.productImageRepository = productImageRepository;
        this.productService = productService;
    }

    public List<ProductImage> findAll(){
        return productImageRepository.findAll();
    }

    public List<ProductImage> slider(List<Long> list){
        List<ProductImage> unique = unique();
        List<ProductImage> returned = new ArrayList<>();
        for(int i = 0; i < unique.size(); i++){
            for(int j = 0; j < list.size(); j++){
                if(unique.get(i).getProduct().getId() == list.get(j)){
                    returned.add(unique.get(i));
                }
            }
        }

        return returned;
    }

    public List<ProductImage> unique(){
        List<ProductImage> tmp = productImageRepository.findAll();
        List<ProductImage> productImages = new ArrayList<>();

        long id = -1;

        for (int i = 0; i < tmp.size(); i++) {
            if (id != tmp.get(i).getProduct().getId()) {
                id = tmp.get(i).getProduct().getId();
                productImages.add(tmp.get(i));
            }
        }
        return productImages;
    }

    public List<ProductImage> findByProductName(String name){
        if(name != null && !name.isEmpty()) {
            return productImageRepository.findByProductName(name);
        }
        return null;
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

    public List<ProductImage> save(long product, List<MultipartFile> image) throws IOException {
        List<ProductImage> list = new ArrayList<>();

        for(int i = 0; i < image.size(); i++) {
            ProductImage productImage = new ProductImage();
            productImage.setProduct(productService.findById(product));

            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String productName = productImage.getProduct().getName().replace(" ", "-");
            productName = productName.replace("/", "-");
            productName = productName.replace("|", "-");

            String resultFileName = UUID.randomUUID().toString() + "." + productName + "." + image.get(i).getOriginalFilename();
            image.get(i).transferTo(new File(uploadPath + "/" + resultFileName));

            productImage.setImage(resultFileName);

            productImageRepository.save(productImage);
            list = productImageRepository.findByProductName(productImage.getProduct().getName());
        }

        for(int i = 0; i < list.size(); i++){
            if (list.get(i).getProduct().getId() != product)
                list.remove(i);
        }

        return list;
    }
}
