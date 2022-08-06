package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.api.assembler.CityInputDisassembler;
import com.github.ludmylla.foodapi.api.assembler.CityModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.CityModel;
import com.github.ludmylla.foodapi.domain.dtos.input.CityInputModel;
import com.github.ludmylla.foodapi.domain.service.exceptions.BusinessException;
import com.github.ludmylla.foodapi.domain.service.exceptions.StateNotFoundException;
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

    @Autowired
    private CityModelAssembler cityModelAssembler;

    @Autowired
    private CityInputDisassembler cityInputDisassembler;

    @PostMapping
    public ResponseEntity<CityModel> create(@RequestBody @Valid CityInputModel cityInput){
        try {
            City city = cityInputDisassembler.toDomainModel(cityInput);
            city = cityService.create(city);
            return ResponseEntity.status(HttpStatus.CREATED).body(cityModelAssembler.toModel(city));
        }catch (StateNotFoundException ex){
            throw new BusinessException(ex.getMessage(), ex);
        }
    }

    @GetMapping
    public ResponseEntity<List<CityModel>> findAll(){
        List<City> list = cityService.findAll();
        return ResponseEntity.ok(cityModelAssembler.toCollectionModel(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityModel> findById(@PathVariable Long id){
        City city = cityService.findById(id);
        return ResponseEntity.ok(cityModelAssembler.toModel(city));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityModel> update(@PathVariable Long id ,@RequestBody @Valid CityInputModel cityInput){
        try {
            City city = cityInputDisassembler.toDomainModel(cityInput);
            City cityUpdate = cityService.update(id, city);
            return ResponseEntity.ok(cityModelAssembler.toModel(cityUpdate));
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
