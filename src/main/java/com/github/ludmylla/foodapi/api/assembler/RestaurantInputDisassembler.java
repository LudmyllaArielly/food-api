package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.api.model.dtos.input.RestaurantInputModel;
import com.github.ludmylla.foodapi.domain.model.Kitchen;
import com.github.ludmylla.foodapi.domain.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantInputDisassembler {

    public Restaurant toDomainModel(RestaurantInputModel restaurantInput){
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantInput.getName());
        restaurant.setFreightRate(restaurantInput.getFreightRate());

        Kitchen kitchen = new Kitchen();
        kitchen.setId(restaurantInput.getKitchen().getId());
        restaurant.setKitchen(kitchen);

        return restaurant;
    }
}
