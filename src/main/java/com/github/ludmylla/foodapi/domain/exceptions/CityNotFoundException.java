package com.github.ludmylla.foodapi.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CityNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public CityNotFoundException(String message){
        super(message);
    }

    public CityNotFoundException(Long id) {
        this(String.format("There is no code city %d" ,id));
    }
}
