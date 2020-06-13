package com.space.datahub.service;

import com.space.datahub.domain.Category;
import com.space.datahub.repo.CategoryRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepo categoryRepository;

    public CategoryService(CategoryRepo categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Category findByName(String name){
        return categoryRepository.findByName(name);
    }

    public List<Category> findByTypeName(String name){
        return categoryRepository.findByTypeName(name);
    }

    public List<Category> findByTypeId(long id) {
        return categoryRepository.findByTypeId(id);
    }

    public void delete(Category category){
        categoryRepository.delete(category);
    }

    public Category save(@RequestBody Category category){
        return categoryRepository.save(category);
    }
}
