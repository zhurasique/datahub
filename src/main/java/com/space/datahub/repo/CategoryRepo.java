package com.space.datahub.repo;

import com.space.datahub.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findByName(String name);
    List<Category> findByTypeName(String type);
    List<Category> findByTypeId(long id);
}
