package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.api.assembler.FormOfPaymentInputDisassembler;
import com.github.ludmylla.foodapi.api.assembler.FormOfPaymentModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.FormOfPaymentModel;
import com.github.ludmylla.foodapi.domain.dtos.input.FormOfPaymentInputModel;
import com.github.ludmylla.foodapi.domain.model.FormOfPayment;
import com.github.ludmylla.foodapi.domain.service.FormOfPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/form-of-payments")
public class FormOfPaymentController {

    @Autowired
    private FormOfPaymentService formOfPaymentService;

    @Autowired
    private FormOfPaymentModelAssembler formOfPaymentModelAssembler;

    @Autowired
    private FormOfPaymentInputDisassembler formOfPaymentInputDisassembler;

    @PostMapping
    public ResponseEntity<FormOfPaymentModel> create(@RequestBody @Valid FormOfPaymentInputModel formOfPaymentInput){
        FormOfPayment formOfPayment = formOfPaymentInputDisassembler.toDomainModel(formOfPaymentInput);
        FormOfPayment formOfPaymentCreate = formOfPaymentService.create(formOfPayment);
        return ResponseEntity.status(HttpStatus.CREATED).body(formOfPaymentModelAssembler.toModel(formOfPaymentCreate));
    }

    @GetMapping
    public ResponseEntity<List<FormOfPaymentModel>> findAll(){
        List<FormOfPayment> list = formOfPaymentService.findAll();
        return ResponseEntity.ok(formOfPaymentModelAssembler.toCollectionModel(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormOfPaymentModel> findById(@PathVariable Long id){
        FormOfPayment formOfPayment = formOfPaymentService.findById(id);
        return ResponseEntity.ok(formOfPaymentModelAssembler.toModel(formOfPayment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormOfPaymentModel> update(@RequestBody @Valid FormOfPaymentInputModel formOfPaymentInput, @PathVariable Long id){
        FormOfPayment formOfPayment = formOfPaymentInputDisassembler.toDomainModel(formOfPaymentInput);
        FormOfPayment formOfPaymentUpdate = formOfPaymentService.update(id, formOfPayment);
        return ResponseEntity.ok(formOfPaymentModelAssembler.toModel(formOfPaymentUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        formOfPaymentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
