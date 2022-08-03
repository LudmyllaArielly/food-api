package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.api.model.dtos.KitchenModel;
import com.github.ludmylla.foodapi.api.model.dtos.RestaurantModel;
import com.github.ludmylla.foodapi.domain.model.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantModelAssembler {

    public RestaurantModel toModel(Restaurant restaurant){
        KitchenModel kitchenModel = new KitchenModel();
        kitchenModel.setId(restaurant.getKitchen().getId());
        kitchenModel.setName(restaurant.getKitchen().getName());

        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(restaurant.getId());
        restaurantModel.setName(restaurant.getName());
        restaurantModel.setFreightRate(restaurant.getFreightRate());
        restaurantModel.setKitchen(kitchenModel);

        return restaurantModel;
    }

    public List<RestaurantModel> toCollectionModel(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(restaurant -> toModel(restaurant))
                .collect(Collectors.toList());
    }
}
