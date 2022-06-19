package com.github.ludmylla.foodapi.domain.repository;

import com.github.ludmylla.foodapi.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>,
        RestaurantRepositoryQuery, JpaSpecificationExecutor<Restaurant> {

    List<Restaurant> findByFreightRateBetween(BigDecimal freightRateInitial, BigDecimal freightRateFinal);

    //@Query("from Restaurant where name like %:name% and kitchen.id = :id")
    List<Restaurant> findByNameLikeAndKitchenId(String name, @Param("id") Long kitchenId);

    //List<Restaurant> findByNameContainingAndKitchenId(String name, Long kitchenId);

    Restaurant findFirstByNameContaining(String name);

    List<Restaurant>  findTop2ByNameContaining(String name);

    int countByKitchenId(Long kitchenId);

    @Query("SELECT SUM(obj.freightRate) from Restaurant obj")
    BigDecimal sumByFreightRate(BigDecimal freightRate);

    @Query("from Restaurant r join r.kitchen left join fetch r.formOfPayments")
    List<Restaurant> findAll();

}
