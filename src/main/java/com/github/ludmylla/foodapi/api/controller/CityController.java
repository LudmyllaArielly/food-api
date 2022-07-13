package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.domain.exceptions.BusinessException;
import com.github.ludmylla.foodapi.domain.exceptions.StateNotFoundException;
import com.github.ludmylla.foodapi.domain.model.City;
import com.github.ludmylla.foodapi.domain.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping
    public ResponseEntity<City> create(@RequestBody @Valid City city){
        try {
            city = cityService.create(city);
            return ResponseEntity.status(HttpStatus.CREATED).body(city);
        }catch (StateNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @GetMapping
    public ResponseEntity<List<City>> findAll(){
        List<City> list = cityService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> findById(@PathVariable Long id){
        City city = cityService.findById(id);
        return ResponseEntity.ok(city);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> update(@PathVariable Long id ,@RequestBody @Valid City city){
        try {
            city = cityService.update(id, city);
            return ResponseEntity.ok(city);
        }catch (StateNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        cityService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
