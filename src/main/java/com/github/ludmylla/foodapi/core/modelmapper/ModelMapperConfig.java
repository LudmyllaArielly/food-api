package com.github.ludmylla.foodapi.core.modelmapper;

import com.github.ludmylla.foodapi.domain.dtos.AddressModel;
import com.github.ludmylla.foodapi.domain.model.Address;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();

        var addressToAddressModel = modelMapper.createTypeMap(Address.class, AddressModel.class);
        addressToAddressModel.<String>addMapping(
               addressSrc -> addressSrc.getCity().getState().getName(),
                (addressModelDest, value) -> addressModelDest.getCity().setState(value));

        return modelMapper;
    }
}
