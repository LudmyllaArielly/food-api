package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.api.model.dtos.input.RestaurantInputModel;
import com.github.ludmylla.foodapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantInputDisassembler {

    @Autowired
    private ModelMapper mapper;

    public Restaurant toDomainModel(RestaurantInputModel restaurantInput){
        return mapper.map(restaurantInput,Restaurant.class);
    }

    public void copyToDomainObject(RestaurantInputModel restaurantInput, Restaurant restaurant){
        mapper.map(restaurantInput, restaurant);
    }
}
