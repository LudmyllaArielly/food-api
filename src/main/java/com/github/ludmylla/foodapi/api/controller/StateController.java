package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.domain.model.State;
import com.github.ludmylla.foodapi.domain.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController {

    @Autowired
    private StateService stateService;

    @PostMapping
    public ResponseEntity<State> create(@RequestBody @Valid State state){
        state = stateService.create(state);
        return ResponseEntity.status(HttpStatus.CREATED).body(state);
    }

    @GetMapping
    public ResponseEntity<List<State>> findAll(){
        List<State> list = stateService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> findById(@PathVariable Long id){
        State state = stateService.findById(id);
        return ResponseEntity.ok(state);
    }


    @PutMapping("/{id}")
    public ResponseEntity<State> update(@PathVariable Long id ,@RequestBody @Valid State state){
        state = stateService.update(id, state);
        return ResponseEntity.ok(state);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        stateService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
