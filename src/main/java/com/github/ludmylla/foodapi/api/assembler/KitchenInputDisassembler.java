package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.api.model.dtos.input.KitchenInputModel;
import com.github.ludmylla.foodapi.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KitchenInputDisassembler {

    @Autowired
    private ModelMapper mapper;

    public Kitchen toDomainModel(KitchenInputModel kitchenInput){
        return mapper.map(kitchenInput,Kitchen.class);
    }
    
}
