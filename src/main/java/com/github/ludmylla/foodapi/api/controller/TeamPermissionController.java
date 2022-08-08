package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.api.assembler.PermissionModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.PermissionModel;
import com.github.ludmylla.foodapi.domain.model.Team;
import com.github.ludmylla.foodapi.domain.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams/{teamId}/permissions")
public class TeamPermissionController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private PermissionModelAssembler permissionModelAssembler;

    @GetMapping
    public ResponseEntity<List<PermissionModel>> findAll(@PathVariable Long teamId){
        Team team = teamService.findById(teamId);
        return ResponseEntity.ok(permissionModelAssembler.toCollectionModel(team.getPermission()));
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<Void> addPermission(@PathVariable Long teamId, @PathVariable Long permissionId){
        teamService.addPermission(teamId, permissionId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> removePermission(@PathVariable Long teamId, @PathVariable Long permissionId){
        teamService.removePermission(teamId, permissionId);
        return ResponseEntity.noContent().build();
    }

}
