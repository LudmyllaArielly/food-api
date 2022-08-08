package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.model.Permission;
import com.github.ludmylla.foodapi.domain.service.exceptions.EntityInUseException;
import com.github.ludmylla.foodapi.domain.service.exceptions.TeamNotFoundException;
import com.github.ludmylla.foodapi.domain.model.Team;
import com.github.ludmylla.foodapi.domain.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamService {

    private static final String MSG_TEAM_IN_USE =  "Code team %d cannot be removed as it is in use";

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PermissionService permissionService;

    @Transactional
    public Team create(Team team){
        return teamRepository.save(team);
    }

    public List<Team> findAll(){
        return teamRepository.findAll();
    }

    public Team findById(Long id){
        return teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));
    }

    @Transactional
    public Team update(Long id, Team team){
        Team teamActual = findById(id);
        team.setId(teamActual.getId());
        return teamRepository.save(team);
    }

    @Transactional
    public void delete(Long id){
        try {
            findById(id);
            teamRepository.deleteById(id);
            teamRepository.flush();

        }catch (EmptyResultDataAccessException e){
            throw new TeamNotFoundException(id);
        }catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_TEAM_IN_USE, id));
        }
    }

    @Transactional
    public void addPermission(Long teamId, Long permissionId){
        Team team = findById(teamId);
        Permission permission = permissionService.findById(permissionId);
        team.addPermission(permission);
    }

    @Transactional
    public void removePermission(Long teamId, Long permissionId){
        Team team = findById(teamId);
        Permission permission = permissionService.findById(permissionId);
        team.removePermission(permission);
    }
}
