package com.github.ludmylla.foodapi.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.ludmylla.foodapi.domain.model.State;

public abstract class CityMixin {

    @JsonIgnoreProperties(value = "name", allowGetters = true)
    private State state;
}
