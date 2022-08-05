package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.domain.dtos.input.TeamInputModel;
import com.github.ludmylla.foodapi.domain.model.Team;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamInputDisassembler {

    @Autowired
    private ModelMapper mapper;

    public Team toDomainModel(TeamInputModel teamInput){
        return mapper.map(teamInput,Team.class);
    }
    
}
