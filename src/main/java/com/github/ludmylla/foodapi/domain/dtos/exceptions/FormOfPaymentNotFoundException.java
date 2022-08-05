package com.github.ludmylla.foodapi.domain.dtos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FormOfPaymentNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public FormOfPaymentNotFoundException(String message){
        super(message);
    }

    public FormOfPaymentNotFoundException(Long id) {
        this(String.format("There is no code Form of payment %d" ,id));
    }
}
