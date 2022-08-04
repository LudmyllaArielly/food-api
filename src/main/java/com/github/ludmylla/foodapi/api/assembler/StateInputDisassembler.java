package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.domain.dtos.input.StateInputModel;
import com.github.ludmylla.foodapi.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StateInputDisassembler {

    @Autowired
    private ModelMapper mapper;

    public State toDomainModel(StateInputModel stateInput){
        return mapper.map(stateInput,State.class);
    }

}
