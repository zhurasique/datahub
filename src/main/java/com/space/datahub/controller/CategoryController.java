package com.space.datahub.controller;

import com.space.datahub.domain.Category;
import com.space.datahub.domain.Type;
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
        Category category = null;
        if(name != null && !name.isEmpty()) {
            category = categoryService.findByName(name);
            return category;
        }
        return null;
    }

    @GetMapping("/type/name")
    public List<Category> byType(@RequestParam String type){
        if(type != null && !type.isEmpty())
            return categoryService.findByTypeName(type);
        return null;
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
        if(byName(name) != null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            Category category = new Category();
            category.setName(name);
            category.setType(findType(type));

            categoryService.save(category);
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
    }

    public Type findType(String name){
        return typeService.findByName(name);
    }
}
