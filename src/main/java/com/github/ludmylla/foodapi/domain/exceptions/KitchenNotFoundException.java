package com.github.ludmylla.foodapi.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class KitchenNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public KitchenNotFoundException(String message){
        super(message);
    }

    public KitchenNotFoundException(Long id) {
        this(String.format("There is no code kitchen %d" ,id));
    }
}
