package com.space.datahub.controller;

import com.space.datahub.domain.Category;
import com.space.datahub.domain.Product;
import com.space.datahub.domain.Type;
import com.space.datahub.service.CategoryService;
import com.space.datahub.service.ProductService;
import com.space.datahub.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final TypeService typeService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, TypeService typeService){
        this.productService = productService;
        this.categoryService = categoryService;
        this.typeService = typeService;
    }

    @GetMapping
    public List<Product> list(){
        return productService.findAll();
    }

    @GetMapping("/id")
    public Product byProductId(@RequestParam String id){
        long parsedId = Long.parseLong(id);
        return productService.findById(parsedId);
    }

    @GetMapping("/name")
    public List<Product> byName(@RequestParam String name){
        if(name != null && !name.isEmpty()) {
            return productService.findByName(name);
        }
        return null;
    }

    @GetMapping("/department/name")
    public List<Product> byTypeD(@RequestParam String department){
        if(department != null && !department.isEmpty()) {
            List<Type> typeList = typeService.findByDepartmentName(department);

            List<Category> categoryList = new ArrayList<>();
            for(int i = 0; i < typeList.size(); i++)
                categoryList.addAll(categoryService.findByTypeName(typeList.get(i).getName()));

            List<Product> productsList = new ArrayList<>();
            for(int i = 0; i < categoryList.size(); i++)
                productsList.addAll(productService.findByCategoryName(categoryList.get(i).getName()));
            return productsList;
        }
        return null;
    }

    @GetMapping("/type/name")
    public List<Product> byTypeS(@RequestParam String type){
        if(type != null && !type.isEmpty()) {
            List<Category> categoryList = categoryService.findByTypeName(type);
            List<Product> productsList = new ArrayList<>();
            for(int i = 0; i < categoryList.size(); i++)
                productsList.addAll(productService.findByCategoryName(categoryList.get(i).getName()));
            return productsList;
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
    public ResponseEntity<?> create(@RequestParam String name, @RequestParam double price, @RequestParam String description, @RequestParam String category) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategory(categoryService.findByName(category));

        productService.save(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
