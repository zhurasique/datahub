package com.space.datahub.controller;

import com.space.datahub.domain.Category;
import com.space.datahub.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    private final CategoryRepo categoryRepository;

    @Autowired
    public CategoryController(CategoryRepo categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> list(){
        return categoryRepository.findAll();
    }

    @GetMapping("/name")
    public Category byName(@RequestParam String name){
        Category category = null;
        if(name != null && !name.isEmpty()) {
            category = categoryRepository.findByName(name);
            return category;
        }
        return null;
    }

    @GetMapping("/type/name")
    public Iterable<Category> byType(@RequestParam String type){
        Iterable<Category> categories = null;
        if(type != null && !type.isEmpty()) {
            categories = categoryRepository.findByTypeName(type);
            return categories;
        }
        return null;
    }

    @GetMapping("/type/id")
    public Iterable<Category> byId(@RequestParam long id){
        Iterable<Category> categories = null;
        categories = categoryRepository.findByTypeId(id);
        return categories;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Category category){
        categoryRepository.delete(category);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Category category){
        if(byName(category.getName()) != null)
            return new ResponseEntity<>(category, HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            categoryRepository.save(category);
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
    }
}
