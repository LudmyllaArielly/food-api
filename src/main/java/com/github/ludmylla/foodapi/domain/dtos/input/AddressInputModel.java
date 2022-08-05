package com.github.ludmylla.foodapi.domain.dtos.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressInputModel {

    @NotBlank
    private String zipCode;

    @NotBlank
    private String publicPlace;

    @NotBlank
    private String number;

    private String addressComplement;

    @NotBlank
    private String district;

    @Valid
    @NotNull
    private CityIdInput city;
}
