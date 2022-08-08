package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.api.assembler.UserModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.UserModel;
import com.github.ludmylla.foodapi.domain.model.Restaurant;
import com.github.ludmylla.foodapi.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/responsible")
public class RestaurantUserResponsibleController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @GetMapping
    public ResponseEntity<List<UserModel>> findAll(@PathVariable Long restaurantId){
        Restaurant restaurant = restaurantService.findById(restaurantId);
        return ResponseEntity.ok(userModelAssembler.toCollectionModel(restaurant.getResponsible()));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> addResponsible(@PathVariable Long restaurantId, @PathVariable Long userId){
        restaurantService.addResponsible(restaurantId, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeResponsible(@PathVariable Long restaurantId, @PathVariable Long userId){
        restaurantService.removeResponsible(restaurantId, userId);
        return ResponseEntity.noContent().build();
    }

}
