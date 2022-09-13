package com.github.ludmylla.foodapi.core.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;
import java.util.stream.Collectors;
/*
* Classe usada para que o filtro de sort permita userName traduzindo
* para user.name
*/
public class PageableTranslator {

    public static Pageable translate(Pageable pageable, Map<String, String> fieldsMapping) {
       var orders = pageable.getSort().stream()
               .filter(order -> fieldsMapping.containsKey(order.getProperty()))
               .map(order -> new Sort.Order(order.getDirection(),
                        fieldsMapping.get(order.getProperty())))
               .collect(Collectors.toList());

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(orders));
    }
}
