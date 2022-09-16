package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.domain.dtos.PhotoProductModel;
import com.github.ludmylla.foodapi.domain.model.PhotoProduct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhotoProductModelAssembler {

    @Autowired
    private ModelMapper mapper;

    public PhotoProductModel toModel(PhotoProduct photoProduct) {
        return mapper.map(photoProduct, PhotoProductModel.class);
    }

}
