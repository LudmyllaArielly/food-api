package com.github.ludmylla.foodapi.domain.model;

public enum OrderStatus {

    CREATED("Created"),
    CONFIRMED("Confirmed"),
    DELIVERED("Delivered"),
    CANCELED("Canceled");

    private String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
