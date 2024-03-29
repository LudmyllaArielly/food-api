package com.github.ludmylla.foodapi.domain.repository;

import com.github.ludmylla.foodapi.domain.model.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Long> {

    List<Kitchen> findByName (String name);

    List<Kitchen> findByNameContaining(String name);

    // Page<Kitchen> findByNameContaining(String name, Pageable pageable);

    boolean existsByName(String name);

}
