package com.github.ludmylla.foodapi.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.ludmylla.foodapi.domain.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public abstract class KitchenMixin {

    @JsonIgnore
    private List<Restaurant> restaurants = new ArrayList<>();
}
