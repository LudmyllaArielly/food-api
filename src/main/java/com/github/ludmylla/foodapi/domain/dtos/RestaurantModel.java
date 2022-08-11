package com.github.ludmylla.foodapi.domain.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.ludmylla.foodapi.domain.dtos.view.RestaurantView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantModel {

    @JsonView({RestaurantView.Resume.class, RestaurantView.NameId.class})
    private Long id;

    @JsonView({RestaurantView.Resume.class, RestaurantView.NameId.class})
    private String name;

    @JsonView(RestaurantView.Resume.class)
    private BigDecimal freightRate;

    @JsonView(RestaurantView.Resume.class)
    private KitchenModel kitchen;

    private Boolean activated;
    private AddressModel address;
    private Boolean open;


}
