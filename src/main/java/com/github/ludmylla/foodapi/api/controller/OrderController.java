package com.github.ludmylla.foodapi.api.controller;


import com.github.ludmylla.foodapi.api.assembler.OrderModelAssembler;
import com.github.ludmylla.foodapi.api.assembler.OrderResumeModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.OrderModel;
import com.github.ludmylla.foodapi.domain.dtos.OrderResumeModel;
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

    @Autowired
    private OrderResumeModelAssembler orderResumeModelAssembler;

    @GetMapping
    public ResponseEntity<List<OrderResumeModel>> findAll(){
        List<Order> list = orderService.findAll();
        return ResponseEntity.ok(orderResumeModelAssembler.toCollectionModel(list));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderModel> findById(@PathVariable Long orderId){
        Order order = orderService.findById(orderId);
        return ResponseEntity.ok(orderModelAssembler.toModel(order));
    }

}
