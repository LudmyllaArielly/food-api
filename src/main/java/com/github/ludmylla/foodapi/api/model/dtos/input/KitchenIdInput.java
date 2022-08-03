package com.github.ludmylla.foodapi.api.model.dtos.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class KitchenIdInput {

    @NotNull
    private Long id;
}
