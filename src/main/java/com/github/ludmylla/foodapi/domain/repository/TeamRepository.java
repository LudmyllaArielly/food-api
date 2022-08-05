package com.github.ludmylla.foodapi.domain.repository;

import com.github.ludmylla.foodapi.domain.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {


}
