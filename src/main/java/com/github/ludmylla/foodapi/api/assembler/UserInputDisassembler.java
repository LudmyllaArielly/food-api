package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.domain.dtos.input.UserInputModel;
import com.github.ludmylla.foodapi.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInputDisassembler {

    @Autowired
    private ModelMapper mapper;

    public User toDomainModel(UserInputModel userInput){
        return mapper.map(userInput,User.class);
    }
}
