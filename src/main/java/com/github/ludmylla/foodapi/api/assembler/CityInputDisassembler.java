package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.domain.dtos.input.CityInputModel;
import com.github.ludmylla.foodapi.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityInputDisassembler {

    @Autowired
    private ModelMapper mapper;

    public City toDomainModel(CityInputModel cityInput){
        return mapper.map(cityInput,City.class);
    }
    
}
