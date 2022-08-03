package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.api.model.dtos.KitchenModel;
import com.github.ludmylla.foodapi.api.model.dtos.RestaurantModel;
import com.github.ludmylla.foodapi.domain.exceptions.BusinessException;
import com.github.ludmylla.foodapi.domain.exceptions.EntityNotFoundException;
import com.github.ludmylla.foodapi.domain.model.Restaurant;
import com.github.ludmylla.foodapi.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Restaurant restaurant){
       Restaurant restaurantCreate = restaurantService.create(restaurant);
       try {
           return ResponseEntity.status(HttpStatus.CREATED).body(restaurantCreate);
       }catch (EntityNotFoundException e){
           throw new BusinessException(e.getMessage());
       }
    }

    @GetMapping
    public ResponseEntity<List<RestaurantModel>> findAll(){
        List<Restaurant> list = restaurantService.findAll();
        return ResponseEntity.ok(toCollectionModel(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantModel> findById(@PathVariable Long id){
        Restaurant restaurant = restaurantService.findById(id);
        return ResponseEntity.ok(toModel(restaurant));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid Restaurant restaurant){
        try {
            Restaurant restaurantUpdate = restaurantService.update(id, restaurant);
            return ResponseEntity.ok().body(restaurant);
        }catch (EntityNotFoundException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> fields, HttpServletRequest request){
        Restaurant restaurant = restaurantService.partialUpdate(id, fields, request);
        return ResponseEntity.ok(restaurant);
    }

    private RestaurantModel toModel(Restaurant restaurant){
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

    private List<RestaurantModel> toCollectionModel(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(restaurant -> toModel(restaurant))
                .collect(Collectors.toList());
    }

}
