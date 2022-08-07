package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.api.assembler.FormOfPaymentModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.FormOfPaymentModel;
import com.github.ludmylla.foodapi.domain.model.Restaurant;
import com.github.ludmylla.foodapi.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/form-of-payments")
public class RestaurantFormOfPaymentController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private FormOfPaymentModelAssembler formOfPaymentModelAssembler;

    @GetMapping
    public ResponseEntity<List<FormOfPaymentModel>> findAll(@PathVariable Long restaurantId){
        Restaurant restaurant = restaurantService.findById(restaurantId);
        return ResponseEntity.ok(formOfPaymentModelAssembler.toCollectionModel(restaurant.getFormOfPayments()));
    }

    @DeleteMapping("/{formOfPaymentId}")
    public ResponseEntity<Void> disassociateFormOfPayment(@PathVariable Long restaurantId, @PathVariable Long formOfPaymentId){
        restaurantService.disassociateFormOfPayment(restaurantId, formOfPaymentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{formOfPaymentId}")
    public ResponseEntity<Void> addFormOfPayment(@PathVariable Long restaurantId, @PathVariable Long formOfPaymentId){
        restaurantService.addFormOfPayment(restaurantId, formOfPaymentId);
        return ResponseEntity.noContent().build();
    }

}
