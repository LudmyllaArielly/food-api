package com.github.ludmylla.foodapi.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantModel {

    private Long id;
    private String name;
    private BigDecimal freightRate;
    private KitchenModel kitchen;
    private Boolean activated;
    private AddressModel address;
    private Boolean open;


}
