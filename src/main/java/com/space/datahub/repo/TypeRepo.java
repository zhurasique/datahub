package com.space.datahub.repo;

import com.space.datahub.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepo extends JpaRepository<Type, Long> {
    Type findByName(String name);
}
