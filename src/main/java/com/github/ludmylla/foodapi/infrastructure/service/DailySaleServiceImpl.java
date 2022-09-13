package com.github.ludmylla.foodapi.infrastructure.service;

import com.github.ludmylla.foodapi.domain.filter.DailySaleFilter;
import com.github.ludmylla.foodapi.domain.model.Order;
import com.github.ludmylla.foodapi.domain.model.OrderStatus;
import com.github.ludmylla.foodapi.domain.model.filter.DailySale;
import com.github.ludmylla.foodapi.domain.service.DailySaleQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* Cria query para fazer consulta de dados agregados */

@Repository
public class DailySaleServiceImpl implements DailySaleQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<DailySale> consultDailySales(DailySaleFilter filter) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(DailySale.class);
        var root = query.from(Order.class);

        var predicates = new ArrayList<Predicate>();

        var functionDateCreation =
                builder.function("date", Date.class, root.get("creationDate"));

        var selection =
                builder.construct(DailySale.class,
                        functionDateCreation,
                        builder.count(root.get("id")),
                        builder.sum(root.get("priceTotal")));

        if(filter.getRestaurantId() != null) {
            predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
        }
        if(filter.getDateCreationStart() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"),
                    filter.getDateCreationStart()));
        }

        if(filter.getDateCreationEnd() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"),
                    filter.getDateCreationEnd()));
        }

        predicates.add(root.get("status").in(
                OrderStatus.CONFIRMED, OrderStatus.DELIVERED));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateCreation);

        return manager.createQuery(query).getResultList();
    }
}
