package com.github.ludmylla.foodapi.api.controller;


import com.github.ludmylla.foodapi.api.assembler.OrderInputDisassembler;
import com.github.ludmylla.foodapi.api.assembler.OrderModelAssembler;
import com.github.ludmylla.foodapi.api.assembler.OrderResumeModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.OrderModel;
import com.github.ludmylla.foodapi.domain.dtos.OrderResumeModel;
import com.github.ludmylla.foodapi.domain.dtos.input.OrderInputModel;
import com.github.ludmylla.foodapi.domain.model.Order;
import com.github.ludmylla.foodapi.domain.model.User;
import com.github.ludmylla.foodapi.domain.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private OrderInputDisassembler orderInputDisassembler;

    @PostMapping
    public ResponseEntity<OrderModel> create(@Valid @RequestBody OrderInputModel orderInput){
        Order newOrder = orderInputDisassembler.toDomainModel(orderInput);

        newOrder.setUser(new User());
        newOrder.getUser().setId(1L);

        newOrder = orderService.issueOrder(newOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderModelAssembler.toModel(newOrder));
    }

    @GetMapping
    public ResponseEntity<List<OrderResumeModel>> findAll(){
        List<Order> list = orderService.findAll();
        return ResponseEntity.ok(orderResumeModelAssembler.toCollectionModel(list));
    }
/*
    @GetMapping("/filter")
    public ResponseEntity<MappingJacksonValue> findAllMapping(@RequestParam(required = false) String field){
        List<Order> list = orderService.findAll();
        List<OrderResumeModel> ordersModel = orderResumeModelAssembler.toCollectionModel(list);

        MappingJacksonValue ordersWrapper = new MappingJacksonValue(ordersModel);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("orderFilter", SimpleBeanPropertyFilter.serializeAll());

        if(StringUtils.isNotBlank(field)){
            filterProvider.addFilter("orderFilter", SimpleBeanPropertyFilter.filterOutAllExcept(field.split(",")));
        }

        ordersWrapper.setFilters(filterProvider);

        return ResponseEntity.ok(ordersWrapper);
    }*/

    @GetMapping("/{code}")
    public ResponseEntity<OrderModel> findById(@PathVariable String code){
        Order order = orderService.findById(code);
        return ResponseEntity.ok(orderModelAssembler.toModel(order));
    }

}
