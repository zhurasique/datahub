package com.space.datahub.controller;

import com.space.datahub.domain.Category;
import com.space.datahub.service.CategoryService;
import com.space.datahub.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final TypeService typeService;

    @Autowired
    public CategoryController(CategoryService categoryService, TypeService typeService) {
        this.categoryService = categoryService;
        this.typeService = typeService;
    }

    @GetMapping
    public List<Category> list(){
        return categoryService.findAll();
    }

    @GetMapping("/name")
    public Category byName(@RequestParam String name){
       return categoryService.findByName(name);
    }

    @GetMapping("/type/name")
    public List<Category> byType(@RequestParam String type){
        return categoryService.findByTypeName(type);
    }

    @GetMapping("/type/id")
    public List<Category> byId(@RequestParam long id){
        return categoryService.findByTypeId(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Category category){
        categoryService.delete(category);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam String name, @RequestParam String type){
        return new ResponseEntity<>(categoryService.save(name, type), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
