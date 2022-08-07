package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.domain.dtos.input.ProductInputModel;
import com.github.ludmylla.foodapi.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductInputDisassembler {

    @Autowired
    private ModelMapper mapper;

    public Product toDomainModel(ProductInputModel productInput){
        return mapper.map(productInput,Product.class);
    }

    public void copyToDomainObject(ProductInputModel productInput, Product product){
        mapper.map(productInput, product);
    }
    
}
