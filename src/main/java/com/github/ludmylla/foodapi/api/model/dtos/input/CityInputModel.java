package com.github.ludmylla.foodapi.api.model.dtos.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityInputModel {

    @NotBlank
    private String name;

    @Valid
    @NotNull
    private StateIdInput state;
}
