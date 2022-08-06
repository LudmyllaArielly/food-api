package com.github.ludmylla.foodapi.domain.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String message){
        super(message);
    }

    public UserNotFoundException(Long id) {
        this(String.format("There is no code city %d" ,id));
    }
}
