package com.github.ludmylla.foodapi.domain.repository;

import com.github.ludmylla.foodapi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
