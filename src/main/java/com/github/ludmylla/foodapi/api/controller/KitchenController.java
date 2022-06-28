package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.domain.model.Kitchen;
import com.github.ludmylla.foodapi.domain.service.KitchenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/kitchens", produces = MediaType.APPLICATION_JSON_VALUE)
public class KitchenController {

    @Autowired
    private KitchenService service;

    @PostMapping
    public ResponseEntity<Kitchen> create(@RequestBody Kitchen kitchen){
        Kitchen kitchens = service.create(kitchen);
        return ResponseEntity.status(HttpStatus.CREATED).body(kitchens);
    }

    @GetMapping
    public ResponseEntity<List<Kitchen>> findAll(){
        List<Kitchen> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kitchen> findById(@PathVariable Long id){
        Kitchen kitchen = service.findById(id);
        return ResponseEntity.ok(kitchen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kitchen> update(@PathVariable Long id, @RequestBody Kitchen kitchen){
        Kitchen kitchenUpdate = service.update(kitchen,id);
        return ResponseEntity.ok(kitchenUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
