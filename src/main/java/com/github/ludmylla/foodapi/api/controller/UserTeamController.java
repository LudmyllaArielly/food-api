package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.api.assembler.TeamModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.TeamModel;
import com.github.ludmylla.foodapi.domain.model.User;
import com.github.ludmylla.foodapi.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/teams")
public class UserTeamController {

    @Autowired
    private UserService userService;

    @Autowired
    private TeamModelAssembler teamModelAssembler;

    @GetMapping
    public ResponseEntity<List<TeamModel>> findAll(@PathVariable Long userId){
        User user = userService.findById(userId);
        return ResponseEntity.ok(teamModelAssembler.toCollectionModel(user.getTeams()));
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<Void> addTeam(@PathVariable Long userId, @PathVariable Long teamId){
        userService.addTeam(userId, teamId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> removeTeam(@PathVariable Long userId, @PathVariable Long teamId){
        userService.removeTeam(userId, teamId);
        return ResponseEntity.noContent().build();
    }
}
