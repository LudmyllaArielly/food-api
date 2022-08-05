package com.github.ludmylla.foodapi.domain.dtos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TeamNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public TeamNotFoundException(String message){
        super(message);
    }

    public TeamNotFoundException(Long id) {
        this(String.format("There is no code Team %d" ,id));
    }
}
