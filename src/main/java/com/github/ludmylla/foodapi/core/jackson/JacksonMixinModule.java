package com.github.ludmylla.foodapi.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.ludmylla.foodapi.api.model.mixin.CityMixin;
import com.github.ludmylla.foodapi.api.model.mixin.KitchenMixin;
import com.github.ludmylla.foodapi.domain.model.City;
import com.github.ludmylla.foodapi.domain.model.Kitchen;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {
    private static final long serialVersionUID = 1L;

    public JacksonMixinModule(){

        setMixInAnnotation(City.class, CityMixin.class);
        setMixInAnnotation(Kitchen.class, KitchenMixin.class);
    }
}
