package com.github.ludmylla.foodapi.domain.repository;

import com.github.ludmylla.foodapi.domain.model.Restaurant;
import com.github.ludmylla.foodapi.domain.repository.spec.RestaurantSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> find(String name, BigDecimal freightRateInitial, BigDecimal freightRateFinal){

        var builder = manager.getCriteriaBuilder();

        var criteria = builder.createQuery(Restaurant.class);
        var root = criteria.from(Restaurant.class); // from Restaurant

        var predicates = new ArrayList<Predicate>();

        if(StringUtils.hasText(name)){
            predicates.add( builder.like(root.get("name"), "%" + name + "%"));
        }

        if(freightRateInitial != null){
            predicates.add( builder.greaterThanOrEqualTo(root.get("freightRate"), freightRateInitial));
        }

        if(freightRateFinal != null){
            predicates.add(builder.lessThanOrEqualTo(root.get("freightRate"), freightRateFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        var query = manager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public List<Restaurant> findWithFreightRateFree(String name) {
        return restaurantRepository.findAll(RestaurantSpecs.withNameSimilar(name).and(RestaurantSpecs.withFreightRateFree()));
    }
}
