package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.api.model.dtos.CityModel;
import com.github.ludmylla.foodapi.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CityModelAssembler {

    @Autowired
    private ModelMapper mapper;

    public CityModel toModel(City city){
        return mapper.map(city, CityModel.class);
    }

    public List<CityModel> toCollectionModel(List<City> cities) {
        return cities.stream()
                .map(city -> toModel(city))
                .collect(Collectors.toList());
    }
}
