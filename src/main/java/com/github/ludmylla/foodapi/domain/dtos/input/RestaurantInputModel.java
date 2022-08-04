package com.github.ludmylla.foodapi.domain.dtos.input;

import com.github.ludmylla.foodapi.core.validation.FreightRate;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantInputModel {

    @NotBlank
    private String name;

    @NotNull
    @FreightRate
    private BigDecimal freightRate;

    @Valid
    @NotNull
    private KitchenIdInput kitchen;

}
