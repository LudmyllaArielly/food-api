package com.github.ludmylla.foodapi.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.ludmylla.foodapi.api.assembler.RestaurantInputDisassembler;
import com.github.ludmylla.foodapi.api.assembler.RestaurantModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.RestaurantModel;
import com.github.ludmylla.foodapi.domain.dtos.input.RestaurantInputModel;
import com.github.ludmylla.foodapi.domain.dtos.view.RestaurantView;
import com.github.ludmylla.foodapi.domain.model.Restaurant;
import com.github.ludmylla.foodapi.domain.service.RestaurantService;
import com.github.ludmylla.foodapi.domain.service.exceptions.BusinessException;
import com.github.ludmylla.foodapi.domain.service.exceptions.CityNotFoundException;
import com.github.ludmylla.foodapi.domain.service.exceptions.KitchenNotFoundException;
import com.github.ludmylla.foodapi.domain.service.exceptions.RestaurantNofFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantModelAssembler modelAssembler;

    @Autowired
    private RestaurantInputDisassembler inputDisassembler;

    @PostMapping
    public ResponseEntity<RestaurantModel> create(@RequestBody @Valid RestaurantInputModel restaurantInput){
        try {
           Restaurant restaurant = inputDisassembler.toDomainModel(restaurantInput);
           return ResponseEntity.status(HttpStatus.CREATED).body(modelAssembler.toModel(restaurantService.create(restaurant)));
       }catch (KitchenNotFoundException | CityNotFoundException e){
           throw new BusinessException(e.getMessage());
       }
    }

    @JsonView(RestaurantView.Resume.class)
    @GetMapping("/resume")
    public ResponseEntity<List<RestaurantModel>> findAll(){
        List<Restaurant> list = restaurantService.findAll();
        return ResponseEntity.ok(modelAssembler.toCollectionModel(list));
    }

    @JsonView(RestaurantView.NameId.class)
    @GetMapping
    public ResponseEntity<List<RestaurantModel>> findAllByNameAndId(){
        return findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantModel> findById(@PathVariable Long id){
        Restaurant restaurant = restaurantService.findById(id);
        return ResponseEntity.ok(modelAssembler.toModel(restaurant));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantModel> update(@PathVariable Long id, @RequestBody @Valid RestaurantInputModel restaurantInput){
        try {
            Restaurant restaurant = inputDisassembler.toDomainModel(restaurantInput);
            return ResponseEntity.ok().body(modelAssembler.toModel(restaurantService.update(id, restaurant)));
        }catch (KitchenNotFoundException | CityNotFoundException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{id}/activated")
    public ResponseEntity<Void> activated (@PathVariable Long id){
        restaurantService.activated(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/inactivated")
    public ResponseEntity<Void> inactivated (@PathVariable Long id){
        restaurantService.inactivated(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/activations")
    public ResponseEntity<Void> activatedMultiple (@RequestBody List<Long> restaurantIds){
        try {
            restaurantService.activated(restaurantIds);
            return ResponseEntity.noContent().build();
        }catch (RestaurantNofFoundException ex){
            throw new BusinessException(ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/inactivation")
    public ResponseEntity<Void> inactivationMultiple (@RequestBody List<Long> restaurantIds){
        try {
            restaurantService.inactivated(restaurantIds);
            return ResponseEntity.noContent().build();
        }catch (RestaurantNofFoundException ex){
            throw new BusinessException(ex.getMessage(), ex);
        }
    }

    @PutMapping("/{restaurantId}/open")
    public ResponseEntity<Void> openRestaurant(@PathVariable Long restaurantId){
        restaurantService.open(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{restaurantId}/close")
    public ResponseEntity<Void> closeRestaurant(@PathVariable Long restaurantId){
        restaurantService.close(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> fields, HttpServletRequest request){
        Restaurant restaurant = restaurantService.partialUpdate(id, fields, request);
        return ResponseEntity.ok(restaurant);
    }

}
