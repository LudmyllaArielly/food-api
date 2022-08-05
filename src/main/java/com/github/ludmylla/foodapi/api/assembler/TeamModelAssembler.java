package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.domain.dtos.TeamModel;
import com.github.ludmylla.foodapi.domain.model.Team;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamModelAssembler {

    @Autowired
    private ModelMapper mapper;

    public TeamModel toModel(Team team){
        return mapper.map(team, TeamModel.class);
    }

    public List<TeamModel> toCollectionModel(List<Team> teams) {
        return teams.stream()
                .map(team -> toModel(team))
                .collect(Collectors.toList());
    }

}
