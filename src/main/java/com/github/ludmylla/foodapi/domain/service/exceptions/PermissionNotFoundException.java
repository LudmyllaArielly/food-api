package com.github.ludmylla.foodapi.domain.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PermissionNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public PermissionNotFoundException(String message){
        super(message);
    }

    public PermissionNotFoundException(Long permissionId) {
        this(String.format("There is no permission record with code %d" ,permissionId));
    }
}
