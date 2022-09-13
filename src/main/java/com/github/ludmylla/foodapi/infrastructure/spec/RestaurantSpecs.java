package com.github.ludmylla.foodapi.infrastructure.spec;

import com.github.ludmylla.foodapi.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecs {

    public static Specification<Restaurant> withFreightRateFree(){
        return (root, query, builder) ->
                builder.equal(root.get("freightRate"), BigDecimal.ZERO);
    }

    public static Specification<Restaurant> withNameSimilar(String name){
        return (root, query, builder) ->
                builder.like(root.get("name"), "%" + name + "%");
    }
}
