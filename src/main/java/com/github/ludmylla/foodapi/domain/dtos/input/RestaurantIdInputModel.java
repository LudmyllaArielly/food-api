package com.github.ludmylla.foodapi.domain.dtos.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RestaurantIdInputModel {

    @NotNull
    private Long id;
}
