package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.api.assembler.StateInputDisassembler;
import com.github.ludmylla.foodapi.api.assembler.StateModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.StateModel;
import com.github.ludmylla.foodapi.domain.dtos.input.StateInputModel;
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

    @Autowired
    private StateInputDisassembler stateInputDisassembler;

    @Autowired
    private StateModelAssembler stateModelAssembler;

    @PostMapping
    public ResponseEntity<StateModel> create(@RequestBody @Valid StateInputModel stateInput){
        State state = stateInputDisassembler.toDomainModel(stateInput);
        state = stateService.create(state);
        return ResponseEntity.status(HttpStatus.CREATED).body(stateModelAssembler.toModel(state));
    }

    @GetMapping
    public ResponseEntity<List<StateModel>> findAll(){
        List<State> list = stateService.findAll();
        return ResponseEntity.ok(stateModelAssembler.toCollectionModel(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateModel> findById(@PathVariable Long id){
        State state = stateService.findById(id);
        return ResponseEntity.ok(stateModelAssembler.toModel(state));
    }


    @PutMapping("/{id}")
    public ResponseEntity<StateModel> update(@PathVariable Long id ,@RequestBody @Valid StateInputModel stateInput){
        State state = stateInputDisassembler.toDomainModel(stateInput);
        State stateUpdate = stateService.update(id, state);
        return ResponseEntity.ok(stateModelAssembler.toModel(stateUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        stateService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
