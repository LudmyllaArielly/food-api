package com.github.ludmylla.foodapi.core.modelmapper;

import com.github.ludmylla.foodapi.domain.dtos.AddressModel;
import com.github.ludmylla.foodapi.domain.dtos.input.ItemOrderInputModel;
import com.github.ludmylla.foodapi.domain.model.Address;
import com.github.ludmylla.foodapi.domain.model.ItemsOrder;
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

        modelMapper.createTypeMap(ItemOrderInputModel.class, ItemsOrder.class)
                .addMappings(mapper -> mapper.skip(ItemsOrder:: setId));

        return modelMapper;
    }
}
