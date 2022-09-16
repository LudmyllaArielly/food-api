package com.github.ludmylla.foodapi.domain.service.exceptions;

public class PhotoProductNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public PhotoProductNotFoundException(String message) {
        super(message);
    }

    public PhotoProductNotFoundException(Long restaurantId, Long productId) {
        this(String.format("There is no product photo record with code %d for restaurant with code %d", productId, restaurantId));
    }
}
