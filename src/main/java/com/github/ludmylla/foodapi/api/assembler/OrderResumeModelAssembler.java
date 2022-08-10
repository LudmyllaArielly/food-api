package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.domain.dtos.OrderResumeModel;
import com.github.ludmylla.foodapi.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderResumeModelAssembler {

    @Autowired
    private ModelMapper mapper;

    public OrderResumeModel toModel(Order order){
        return mapper.map(order, OrderResumeModel.class);
    }

    public List<OrderResumeModel> toCollectionModel(List<Order> orders) {
        return orders.stream()
                .map(order -> toModel(order))
                .collect(Collectors.toList());
    }
}
