package com.space.datahub.service;

import com.space.datahub.domain.Category;
import com.space.datahub.repo.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepo categoryRepository;
    private final TypeService typeService;

    public CategoryService(CategoryRepo categoryRepository, TypeService typeService) {
        this.categoryRepository = categoryRepository;
        this.typeService = typeService;
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Category findByName(String name){
        Category category = null;
        if(name != null && !name.isEmpty()) {
            category = categoryRepository.findByName(name);
            return category;
        }
        return null;
    }

    public List<Category> findByTypeName(String type){
        System.out.println(categoryRepository.findByTypeName(type));
        if(type != null && !type.isEmpty())
            return categoryRepository.findByTypeName(type);
        return null;
    }

    public List<Category> findByTypeId(long id) {
        return categoryRepository.findByTypeId(id);
    }

    public void delete(Category category){
        categoryRepository.delete(category);
    }

    public Category save(String name, String type){
        if(findByName(name) != null)
            return null;
        else {
            Category category = new Category();
            category.setName(name);
            category.setType(typeService.findByName(type));

            categoryRepository.save(category);
            return category;
        }
    }
}
