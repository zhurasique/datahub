package com.space.datahub.repo;

import com.space.datahub.domain.Bag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BagRepo extends JpaRepository<Bag, Long> {
    Bag findByUserUsername(String name);
    Bag findByName(String name);
}
