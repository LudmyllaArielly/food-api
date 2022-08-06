package com.github.ludmylla.foodapi.domain.dtos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StateNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public StateNotFoundException(String message){
        super(message);
    }

    public StateNotFoundException(Long id) {
        this(String.format("There is no code state %d" ,id));
    }
}