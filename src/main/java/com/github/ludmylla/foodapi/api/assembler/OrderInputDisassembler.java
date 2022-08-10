package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.domain.dtos.input.OrderInputModel;
import com.github.ludmylla.foodapi.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderInputDisassembler {

    @Autowired
    private ModelMapper mapper;

    public Order toDomainModel(OrderInputModel orderInput){
        return mapper.map(orderInput,Order.class);
    }

    public void copyToDomainObject(OrderInputModel orderInput, Order order){
        mapper.map(orderInput, order);
    }
}
