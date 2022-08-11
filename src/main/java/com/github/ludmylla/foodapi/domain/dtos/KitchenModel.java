package com.github.ludmylla.foodapi.domain.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.ludmylla.foodapi.domain.dtos.view.RestaurantView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KitchenModel {

    @JsonView(RestaurantView.Resume.class)
    private Long id;

    @JsonView(RestaurantView.Resume.class)
    private String name;
}
