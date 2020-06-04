package com.space.datahub.repo;

import com.space.datahub.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepo extends JpaRepository<Type, Long> {
    Type findByName(String name);
    List<Type> findByDepartmentName(String department);
    List<Type> findByDepartmentId(long id);
}
