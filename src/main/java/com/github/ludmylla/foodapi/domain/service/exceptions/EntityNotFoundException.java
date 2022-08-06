package com.github.ludmylla.foodapi.domain.service.exceptions;

public abstract class EntityNotFoundException extends BusinessException{
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String message){
        super(message);
    }
}
