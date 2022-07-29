package com.github.ludmylla.foodapi.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.ludmylla.foodapi.domain.model.Address;
import com.github.ludmylla.foodapi.domain.model.FormOfPayment;
import com.github.ludmylla.foodapi.domain.model.Kitchen;
import com.github.ludmylla.foodapi.domain.model.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class RestaurantMixin {

    @JsonIgnore
    private LocalDateTime registrationDate;

    @JsonIgnore
    private LocalDateTime updateDate;

    @JsonIgnoreProperties(value = "name", allowGetters = true)
    private Kitchen kitchen;

    @JsonIgnore
    private Address address;

    @JsonIgnore
    private List<FormOfPayment> formOfPayments = new ArrayList<>();

    @JsonIgnore
    private List<Product> products = new ArrayList<>();
}
