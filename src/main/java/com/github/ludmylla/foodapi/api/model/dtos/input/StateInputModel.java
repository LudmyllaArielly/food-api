package com.github.ludmylla.foodapi.api.model.dtos.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StateInputModel {

    @NotBlank
    private String name;
}
