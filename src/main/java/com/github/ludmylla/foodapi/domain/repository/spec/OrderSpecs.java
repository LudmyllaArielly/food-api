package com.github.ludmylla.foodapi.domain.repository.spec;

import com.github.ludmylla.foodapi.domain.model.Order;
import com.github.ludmylla.foodapi.domain.repository.filter.OrderFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class OrderSpecs {

    public static Specification<Order> usingFilter(OrderFilter filter) {
        return (root, query, builder) -> {

            root.fetch("restaurant").fetch("kitchen");
            root.fetch("user");

            var predicates = new ArrayList<Predicate>();

            if(filter.getUserId() != null){
                predicates.add(builder.equal(root.get("user"), filter.getUserId()));
            }

            if(filter.getRestaurantId() != null){
                predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
            }

            if(filter.getDateCreationStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), filter.getDateCreationStart()));
            }

            if(filter.getDateCreationEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), filter.getDateCreationEnd()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
