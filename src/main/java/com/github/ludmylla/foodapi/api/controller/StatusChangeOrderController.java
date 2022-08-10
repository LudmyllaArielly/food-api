package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.domain.service.StatusChangeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/{orderId}")
public class StatusChangeOrderController {

    @Autowired
    private StatusChangeOrderService statusChangeOrderService;

    @PutMapping("/confirm")
    public ResponseEntity<Void> confirm(@PathVariable Long orderId){
        statusChangeOrderService.confirm(orderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long orderId){
        statusChangeOrderService.cancel(orderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/delivery")
    public ResponseEntity<Void> delivery(@PathVariable Long orderId){
        statusChangeOrderService.delivery(orderId);
        return ResponseEntity.noContent().build();
    }

}
