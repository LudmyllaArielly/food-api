package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.domain.dtos.StateModel;
import com.github.ludmylla.foodapi.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StateModelAssembler {

    @Autowired
    private ModelMapper mapper;

    public StateModel toModel(State state){
        return mapper.map(state, StateModel.class);
    }

    public List<StateModel> toCollectionModel(List<State> states) {
        return states.stream()
                .map(state -> toModel(state))
                .collect(Collectors.toList());
    }
}
