package com.space.datahub.controller;

import com.space.datahub.domain.Category;
import com.space.datahub.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
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
    public ResponseEntity<?> create(@Valid @RequestBody Category category){
        if(byName(category.getName()) != null)
            return new ResponseEntity<>(category, HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            categoryService.save(category);
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
    }
}
