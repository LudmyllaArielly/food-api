package com.github.ludmylla.foodapi.domain.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModel {

    private String zipCode;
    private String publicPlace;
    private String number;
    private String addressComplement;
    private String district;

    private CityResumeModel city;
}
