package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.api.assembler.KitchenInputDisassembler;
import com.github.ludmylla.foodapi.api.assembler.KitchenModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.KitchenModel;
import com.github.ludmylla.foodapi.domain.dtos.input.KitchenInputModel;
import com.github.ludmylla.foodapi.domain.model.Kitchen;
import com.github.ludmylla.foodapi.domain.service.KitchenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/kitchens", produces = MediaType.APPLICATION_JSON_VALUE)
public class KitchenController {

    @Autowired
    private KitchenService service;

    @Autowired
    private KitchenInputDisassembler kitchenInputDisassembler;

    @Autowired
    private KitchenModelAssembler kitchenModelAssembler;

    @PostMapping
    public ResponseEntity<KitchenModel> create(@RequestBody @Valid KitchenInputModel kitchenInput){
        Kitchen kitchen = kitchenInputDisassembler.toDomainModel(kitchenInput);
        Kitchen kitchenCreate = service.create(kitchen);
        return ResponseEntity.status(HttpStatus.CREATED).body(kitchenModelAssembler.toModel(kitchenCreate));
    }

    @GetMapping
    public ResponseEntity<List<KitchenModel>> findAll(){
        List<Kitchen> list = service.findAll();
        return ResponseEntity.ok(kitchenModelAssembler.toCollectionModel(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<KitchenModel> findById(@PathVariable Long id){
        Kitchen kitchen = service.findById(id);
        return ResponseEntity.ok(kitchenModelAssembler.toModel(kitchen));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KitchenModel> update(@PathVariable Long id, @RequestBody @Valid KitchenInputModel kitchenInput){
        Kitchen kitchen = kitchenInputDisassembler.toDomainModel(kitchenInput);
        Kitchen kitchenUpdate = service.update(kitchen,id);
        return ResponseEntity.ok(kitchenModelAssembler.toModel(kitchenUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
