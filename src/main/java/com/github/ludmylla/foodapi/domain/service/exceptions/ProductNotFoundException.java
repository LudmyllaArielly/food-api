package com.github.ludmylla.foodapi.domain.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(String message){
        super(message);
    }

    public ProductNotFoundException(Long productId, Long restaurantId) {
        this(String.format("There is no product registration with code %d for the code restaurant.", productId, restaurantId));
    }
}
