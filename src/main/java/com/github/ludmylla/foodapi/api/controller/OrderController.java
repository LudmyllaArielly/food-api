package com.github.ludmylla.foodapi.api.controller;


import com.github.ludmylla.foodapi.api.assembler.OrderModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.OrderModel;
import com.github.ludmylla.foodapi.domain.model.Order;
import com.github.ludmylla.foodapi.domain.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderModelAssembler orderModelAssembler;

    @GetMapping
    public ResponseEntity<List<OrderModel>> findAll(){
        List<Order> list = orderService.findAll();
        return ResponseEntity.ok(orderModelAssembler.toCollectionModel(list));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderModel> findById(@PathVariable Long orderId){
        Order order = orderService.findById(orderId);
        return ResponseEntity.ok(orderModelAssembler.toModel(order));
    }

}
