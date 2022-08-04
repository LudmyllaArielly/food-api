package com.github.ludmylla.foodapi.api.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityModel {

    private Long id;
    private String name;
    private StateModel state;
}
