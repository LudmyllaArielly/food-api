package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.api.assembler.RestaurantInputDisassembler;
import com.github.ludmylla.foodapi.api.assembler.RestaurantModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.RestaurantModel;
import com.github.ludmylla.foodapi.domain.dtos.input.RestaurantInputModel;
import com.github.ludmylla.foodapi.domain.exceptions.BusinessException;
import com.github.ludmylla.foodapi.domain.exceptions.EntityNotFoundException;
import com.github.ludmylla.foodapi.domain.exceptions.KitchenNotFoundException;
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

       Restaurant restaurant = inputDisassembler.toDomainModel(restaurantInput);
       Restaurant restaurantCreate = restaurantService.create(restaurant);
       try {
           return ResponseEntity.status(HttpStatus.CREATED).body(modelAssembler.toModel(restaurantCreate));
       }catch (KitchenNotFoundException ex){
           throw new BusinessException(ex.getMessage());
       }
    }

    @GetMapping
    public ResponseEntity<List<RestaurantModel>> findAll(){
        List<Restaurant> list = restaurantService.findAll();
        return ResponseEntity.ok(modelAssembler.toCollectionModel(list));
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
        }catch (EntityNotFoundException e){
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

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> fields, HttpServletRequest request){
        Restaurant restaurant = restaurantService.partialUpdate(id, fields, request);
        return ResponseEntity.ok(restaurant);
    }

}
