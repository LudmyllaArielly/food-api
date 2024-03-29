package com.github.ludmylla.foodapi.domain.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public OrderNotFoundException(String code) {
        super(String.format("There is no code order %s" ,code));
    }
}
