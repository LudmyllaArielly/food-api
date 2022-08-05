package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.api.assembler.TeamInputDisassembler;
import com.github.ludmylla.foodapi.api.assembler.TeamModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.TeamModel;
import com.github.ludmylla.foodapi.domain.dtos.input.TeamInputModel;
import com.github.ludmylla.foodapi.domain.model.Team;
import com.github.ludmylla.foodapi.domain.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamInputDisassembler teamInputDisassembler;

    @Autowired
    private TeamModelAssembler teamModelAssembler;

    @PostMapping
    public ResponseEntity<TeamModel> create(@RequestBody @Valid TeamInputModel teamInput){
        Team team = teamInputDisassembler.toDomainModel(teamInput);
        Team teamCreate = teamService.create(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(teamModelAssembler.toModel(teamCreate));
    }

    @GetMapping
    public ResponseEntity<List<TeamModel>> findAll(){
        List<Team> list = teamService.findAll();
        return ResponseEntity.ok(teamModelAssembler.toCollectionModel(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamModel> findById(@PathVariable Long id){
        Team team = teamService.findById(id);
        return ResponseEntity.ok(teamModelAssembler.toModel(team));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamModel> update(@PathVariable Long id, @RequestBody @Valid TeamInputModel teamInput){
        Team team = teamInputDisassembler.toDomainModel(teamInput);
        Team teamUpdate = teamService.update(id, team);
        return ResponseEntity.ok(teamModelAssembler.toModel(teamUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
