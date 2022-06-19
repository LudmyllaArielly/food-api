package com.github.ludmylla.foodapi.domain.repository;

import com.github.ludmylla.foodapi.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepositoryQuery {

    List<Restaurant> find(String name, BigDecimal freightRateInitial, BigDecimal freightRateFinal);

    List<Restaurant> findWithFreightRateFree(String name);
}
