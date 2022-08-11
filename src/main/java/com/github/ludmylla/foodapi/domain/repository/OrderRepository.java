package com.github.ludmylla.foodapi.domain.repository;

import com.github.ludmylla.foodapi.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("from Order o join fetch o.user join fetch o.restaurant k join fetch k.kitchen")
    List<Order> findAll();

    Optional<Order> findByCode(String code);
}
