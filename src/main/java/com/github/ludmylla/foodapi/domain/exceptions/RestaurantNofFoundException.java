package com.github.ludmylla.foodapi.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestaurantNofFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public RestaurantNofFoundException(String message){
        super(message);
    }

    public RestaurantNofFoundException(Long id) {
        this(String.format("There is no code restaurant %d" ,id));
    }
}
