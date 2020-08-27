package com.space.datahub.service;

import com.space.datahub.domain.Category;
import com.space.datahub.domain.Product;
import com.space.datahub.domain.Type;
import com.space.datahub.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepo productRepository;
    private final CategoryService categoryService;
    private final TypeService typeService;

    public ProductService(ProductRepo productRepository, CategoryService categoryService, TypeService typeService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.typeService = typeService;
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public List<Product> findByName(String name){
        if(name != null && !name.isEmpty()) {
            List<Product> tmp = findAll();
            List<Product> productList = new ArrayList<>();
            for(int i = 0; i < tmp.size(); i++){
                if(tmp.get(i).getName().equals(name)) {
                    productList.add(tmp.get(i));
                }
            }
            return productList;
        }
        return null;
    }
    public List<Product> findByCategoryName(String category){
        if(category != null && !category.isEmpty())
            return productRepository.findByCategoryName(category);
        return null;
    }

    public List<Product> byTypeD(String department){
        if(department != null && !department.isEmpty()) {
            List<Type> typeList = typeService.findByDepartmentName(department);

            List<Category> categoryList = new ArrayList<>();
            for(int i = 0; i < typeList.size(); i++)
                categoryList.addAll(categoryService.findByTypeName(typeList.get(i).getName()));

            List<Product> productsList = new ArrayList<>();
            for(int i = 0; i < categoryList.size(); i++)
                productsList.addAll(findByCategoryName(categoryList.get(i).getName()));
            return productsList;
        }
        return null;
    }

    public List<Product> byTypeS(String type){
        if(type != null && !type.isEmpty()) {
            List<Category> categoryList = categoryService.findByTypeName(type);
            List<Product> productsList = new ArrayList<>();
            for(int i = 0; i < categoryList.size(); i++)
                productsList.addAll(findByCategoryName(categoryList.get(i).getName()));
            return productsList;
        }
        return null;
    }

    public Product findById(String id){
        long parsedId = Long.parseLong(id);
        Optional<Product> optional = productRepository.findById(parsedId);
        List<Product> product = new ArrayList<>();
        optional.ifPresent(opt -> {
            product.add(opt);
        });
        return product.get(0);
    }

    public Product findById(long id){
        Optional<Product> optional = productRepository.findById(id);
        List<Product> product = new ArrayList<>();
        optional.ifPresent(opt -> {
            product.add(opt);
        });
        return product.get(0);
    }

    public List<Product> findByCategoryId(long id) {
        return productRepository.findByCategoryId(id);
    }


    public Product save(String name, double price, String description, String category){
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategory(categoryService.findByName(category));

        productRepository.save(product);
        return product;
    }
}
